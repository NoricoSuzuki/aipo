/*
 * Copyright 2000-2004 The Apache Software Foundation.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.jetspeed.services.webpage;

// java.io
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;

// java.util
import java.util.HashMap;

// java.net
import java.net.URL;
import java.net.URLConnection;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;

import org.apache.log4j.Logger;


/*
 * Implements the WebPage service's static resource cache, for optimizing access to 
 * static resources from proxied hosts. Resources currently cached are read-only, static
 * resources such as images, style sheets and scripts.
 *
 */

public class WebPageCache
{

    // The Cache
    private static HashMap cache = new HashMap();

    // the log file singleton instance
    static Logger log = Logger.getLogger(WebPageCache.class);

    /**
      * Given a cacheable web resource, writes the content of that resource to the servlet
      * output stream. The first time that the resource is requested, the content is
      * fetched from the proxied host. From then on, the resource content is served up from
      * the cache.
      *
      * @param resource the resource that is being cached.
      * @param neid the network element id.
      * @param base the proxied host's base URL.      
      * @param host the Proxy Server base URL.
      * @param data the RunData for the request.
      * @return boolean true if the resource was read from the cache, 
      *         false if read from the server.
      */
    public static boolean getResourceFromCache(String resource, 
                                               long sid, 
                                               String base,
                                               String host,
                                               ProxyRunData data)
    {
        try
        {
            CachedResource cr = (CachedResource)cache.get(resource);
            if (null != cr) // is it in the cache...
            {      
                // yes, return cached item
                byte[] bytes = cr.getContent();                
                data.getResponse().getOutputStream().write(bytes, 0, bytes.length);
                return true;
            }
        
            // not found in cache, so get it from proxied host
            URL baseURL = new URL(base);
            URL u = new URL(baseURL, resource);
            HttpURLConnection con = (HttpURLConnection)u.openConnection();  
        
            con.setDoInput(true);
            con.setAllowUserInteraction(false);        
        
            int contentType = WebPageHelper.getContentType(con.getHeaderField("content-type"), resource);        
        
            byte[] content;

            // get the proxied content, if its script, rewrite it
            if (WebPageHelper.CT_JS == contentType)
                content = rewriteScript(con, sid, host, data, resource, base);
            else
                content = getContentAndWrite(con, data);
        
            // create a new cached resource and put it in the cache
            cr = new CachedResource(contentType, content);
            cache.put(resource, cr);

        }
        catch (MalformedURLException ex)
        {
            log.error("CACHE URL EX:" +  ex);
            return false;

        }
        catch (IOException ex)
        {
            log.error("CACHE IO:" +  ex);
            return false;
        }
        return true;
    }


   /**
     * Determines if a resource is cacheable, dependent on the extension:
     *   defined in CACHEABLE_RESOURCES (gif, jpeg, jpg, png, js, css)
     *
     * @param resource the resource that is being proxied.
     * @return boolean true if the resource is a cacheable, otherwise false.
     *
     */
    public static String[] CACHEABLE_RESOURCES = {
        ".gif", ".jpeg", ".jpg", ".png", ".js", ".css" };

    public static boolean isCacheableResource(String resource)
    {
        int pos = resource.lastIndexOf('.');
        if (pos == -1) 
            return false;

        if (resource.endsWith(".html")) 
            return false;

        int length = resource.length();
        if (pos >= length) 
            return false;

        String ext = resource.substring(pos);
        for (int ix=0; ix < CACHEABLE_RESOURCES.length; ix++) {
            if (ext.equalsIgnoreCase(CACHEABLE_RESOURCES[ix])) {
                return true;
            }
        }
        return false;        
    }

    
    /**
      * Retrieves the content from the proxied host for the requested.
      * Per cacheable resource, this is only called once. All further requests will 
      * return the cached content. The content is immediately written to the servlet's 
      * response output stream.
      *
      * @param con the HTTP connection to the proxied host.
      * @param response the servlet response.
      * @return byte[] the resource content, which will be stored in the cache.
      */
    public static byte[] getContentAndWrite(URLConnection con,
                                            ProxyRunData data) throws IOException 
    {
        int CAPACITY = 4096;
    
        InputStream is = con.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] bytes = new byte[CAPACITY];
    
        int readCount = 0;
        while( ( readCount = is.read( bytes )) > 0 ) {
    
            buffer.write( bytes, 0, readCount);
            data.getResponse().getOutputStream().write(bytes, 0, readCount);
        }        
    
        is.close();
        return buffer.toByteArray();
    }

    /**
      * Retrieves the script content from the proxied host for the requested.
      * Per cacheable resource, this is only called once. All further requests will 
      * return the cached content. The content is first rewritten, rewriting all links
      * found in the script back to the Proxy server. Then, the content is immediately 
      * written to the servlet's response output stream.
      *
      * @param con the HTTP connection to the proxied host.
      * @param response the servlet response.
      * @return byte[] the resource content, which will be stored in the cache.
      */    
    public static byte[] rewriteScript(URLConnection con,
                                       long sid,
                                       String host,
                                       ProxyRunData data,
                                       String resource,
                                       String base)
                            throws IOException 
    {
        int CAPACITY = 4096;  

        Configuration config = Configuration.getInstance();
        InputStream is = con.getInputStream();
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] bytes = new byte[CAPACITY];
    
        FileOutputStream fos = null;
        boolean logging = config.getEnableContentLog();

        // log content to a file if enabled
        if (logging) 
        {
            String fileName = data.getServlet().getServletContext().getRealPath(
                                    config.getLogLocation() );
            fos = new FileOutputStream(fileName, true);
            WebPageHelper.writeHeader(fos, resource);
        }

        int readCount = 0;

        // read in the script 
        while( ( readCount = is.read( bytes )) > 0 ) {  
            
            buffer.write( bytes, 0, readCount);
            if (logging)
                fos.write( bytes, 0, readCount);
        }        
    
        if (logging) 
            fos.close();

        is.close();


        String script = buffer.toString();


        if (sid == -1)
        {   // FIXME: I seem to have lost this code....
            // return HTMLRewriter.rewriteScript(script, resource, host, base);
        }
        return script.getBytes();

        // FIXME:  not rewriting scripts...
        // return Rewriter.rewriteScript(script, sid, proxyHost, base);

    }


}
