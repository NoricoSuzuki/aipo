package org.apache.jetspeed.om.dbpsml;


import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.torque.TorqueException;
import org.apache.torque.om.BaseObject;
import org.apache.torque.om.ComboKey;
import org.apache.torque.om.DateKey;
import org.apache.torque.om.NumberKey;
import org.apache.torque.om.ObjectKey;
import org.apache.torque.om.SimpleKey;
import org.apache.torque.om.StringKey;
import org.apache.torque.om.Persistent;
import org.apache.torque.util.Criteria;
import org.apache.torque.util.Transaction;


/**
 * This class was autogenerated by Torque on:
 *
 * [Thu Jun 10 23:17:32 JST 2004]
 *
 * You should not use this class directly.  It should not even be
 * extended all references should be to JetspeedUserProfile
 */
public abstract class BaseJetspeedUserProfile extends BaseObject
{
    /** The Peer class */
    private static final JetspeedUserProfilePeer peer =
        new JetspeedUserProfilePeer();

      
    /** The value for the psmlId field */
    private int psmlId;
      
    /** The value for the userName field */
    private String userName;
      
    /** The value for the mediaType field */
    private String mediaType;
      
    /** The value for the language field */
    private String language;
      
    /** The value for the country field */
    private String country;
      
    /** The value for the page field */
    private String page;
      
    /** The value for the profile field */
    private byte[] profile;
  
  
    /**
     * Get the PsmlId
     * @return int
     */
    public int getPsmlId()
    {
        return psmlId;
    }

                        
    /**
     * Set the value of PsmlId
     */
    public void setPsmlId(int v ) 
    {
    
                  if (this.psmlId != v)
              {
            this.psmlId = v;
            setModified(true);
        }
    
          
              }

  
    /**
     * Get the UserName
     * @return String
     */
    public String getUserName()
    {
        return userName;
    }

                        
    /**
     * Set the value of UserName
     */
    public void setUserName(String v ) 
    {
    
                  if (!ObjectUtils.equals(this.userName, v))
              {
            this.userName = v;
            setModified(true);
        }
    
          
              }

  
    /**
     * Get the MediaType
     * @return String
     */
    public String getMediaType()
    {
        return mediaType;
    }

                        
    /**
     * Set the value of MediaType
     */
    public void setMediaType(String v ) 
    {
    
                  if (!ObjectUtils.equals(this.mediaType, v))
              {
            this.mediaType = v;
            setModified(true);
        }
    
          
              }

  
    /**
     * Get the Language
     * @return String
     */
    public String getLanguage()
    {
        return language;
    }

                        
    /**
     * Set the value of Language
     */
    public void setLanguage(String v ) 
    {
    
                  if (!ObjectUtils.equals(this.language, v))
              {
            this.language = v;
            setModified(true);
        }
    
          
              }

  
    /**
     * Get the Country
     * @return String
     */
    public String getCountry()
    {
        return country;
    }

                        
    /**
     * Set the value of Country
     */
    public void setCountry(String v ) 
    {
    
                  if (!ObjectUtils.equals(this.country, v))
              {
            this.country = v;
            setModified(true);
        }
    
          
              }

  
    /**
     * Get the Page
     * @return String
     */
    public String getPage()
    {
        return page;
    }

                        
    /**
     * Set the value of Page
     */
    public void setPage(String v ) 
    {
    
                  if (!ObjectUtils.equals(this.page, v))
              {
            this.page = v;
            setModified(true);
        }
    
          
              }

  
    /**
     * Get the Profile
     * @return byte[]
     */
    public byte[] getProfile()
    {
        return profile;
    }

                        
    /**
     * Set the value of Profile
     */
    public void setProfile(byte[] v ) 
    {
    
                  if (!ObjectUtils.equals(this.profile, v))
              {
            this.profile = v;
            setModified(true);
        }
    
          
              }

  
         
                
    private static List fieldNames = null;

    /**
     * Generate a list of field names.
     */
    public static synchronized List getFieldNames()
    {
        if (fieldNames == null)
        {
            fieldNames = new ArrayList();
              fieldNames.add("PsmlId");
              fieldNames.add("UserName");
              fieldNames.add("MediaType");
              fieldNames.add("Language");
              fieldNames.add("Country");
              fieldNames.add("Page");
              fieldNames.add("Profile");
              fieldNames = Collections.unmodifiableList(fieldNames);
        }
        return fieldNames;
    }

    /**
     * Retrieves a field from the object by name passed in
     * as a String.
     */
    public Object getByName(String name)
    {
          if (name.equals("PsmlId"))
        {
                return new Integer(getPsmlId());
            }
          if (name.equals("UserName"))
        {
                return getUserName();
            }
          if (name.equals("MediaType"))
        {
                return getMediaType();
            }
          if (name.equals("Language"))
        {
                return getLanguage();
            }
          if (name.equals("Country"))
        {
                return getCountry();
            }
          if (name.equals("Page"))
        {
                return getPage();
            }
          if (name.equals("Profile"))
        {
                return getProfile();
            }
          return null;
    }
    
    /**
     * Retrieves a field from the object by name passed in
     * as a String.  The String must be one of the static
     * Strings defined in this Class' Peer.
     */
    public Object getByPeerName(String name)
    {
          if (name.equals(JetspeedUserProfilePeer.PSML_ID ))
        {
                return new Integer(getPsmlId());
            }
          if (name.equals(JetspeedUserProfilePeer.USER_NAME ))
        {
                return getUserName();
            }
          if (name.equals(JetspeedUserProfilePeer.MEDIA_TYPE ))
        {
                return getMediaType();
            }
          if (name.equals(JetspeedUserProfilePeer.LANGUAGE ))
        {
                return getLanguage();
            }
          if (name.equals(JetspeedUserProfilePeer.COUNTRY ))
        {
                return getCountry();
            }
          if (name.equals(JetspeedUserProfilePeer.PAGE ))
        {
                return getPage();
            }
          if (name.equals(JetspeedUserProfilePeer.PROFILE ))
        {
                return getProfile();
            }
          return null;
    }

    /**
     * Retrieves a field from the object by Position as specified
     * in the xml schema.  Zero-based.
     */
    public Object getByPosition(int pos)
    {
            if ( pos == 0 )
        {
                return new Integer(getPsmlId());
            }
              if ( pos == 1 )
        {
                return getUserName();
            }
              if ( pos == 2 )
        {
                return getMediaType();
            }
              if ( pos == 3 )
        {
                return getLanguage();
            }
              if ( pos == 4 )
        {
                return getCountry();
            }
              if ( pos == 5 )
        {
                return getPage();
            }
              if ( pos == 6 )
        {
                return getProfile();
            }
              return null;
    }
     
    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
     */
    public void save() throws Exception
    {
          save(JetspeedUserProfilePeer.getMapBuilder()
                .getDatabaseMap().getName());
      }

    /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.
       * Note: this code is here because the method body is
     * auto-generated conditionally and therefore needs to be
     * in this file instead of in the super class, BaseObject.
       */
    public void save(String dbName) throws TorqueException
    {
        Connection con = null;
          try
        {
            con = Transaction.begin(dbName);
            save(con);
            Transaction.commit(con);
        }
        catch(TorqueException e)
        {
            Transaction.safeRollback(con);
            throw e;
        }
      }

      /** flag to prevent endless save loop, if this object is referenced
        by another object which falls in this transaction. */
    private boolean alreadyInSave = false;
      /**
     * Stores the object in the database.  If the object is new,
     * it inserts it; otherwise an update is performed.  This method
     * is meant to be used as part of a transaction, otherwise use
     * the save() method and the connection details will be handled
     * internally
     */
    public void save(Connection con) throws TorqueException
    {
          if (!alreadyInSave)
        {
            alreadyInSave = true;


  
            // If this object has been modified, then save it to the database.
            if (isModified())
            {
                if (isNew())
                {
                    JetspeedUserProfilePeer.doInsert((JetspeedUserProfile)this, con);
                    setNew(false);
                }
                else
                {
                    JetspeedUserProfilePeer.doUpdate((JetspeedUserProfile)this, con);
                }

                      if (isCacheOnSave())
                {
                    JetspeedUserProfileManager.putInstance(this);
                }
              }

                      alreadyInSave = false;
        }
      }

    /**
     * Specify whether to cache the object after saving to the db.
     * This method returns false
     */
    protected boolean isCacheOnSave()
    {
        return true;
    }

                  
      /**
     * Set the PrimaryKey using ObjectKey.
     *
     * @param ObjectKey psmlId
     */
    public void setPrimaryKey(ObjectKey psmlId)
         {
            setPsmlId(((NumberKey)psmlId).intValue());
        }

    /**
     * Set the PrimaryKey using a String.
     */
    public void setPrimaryKey(String key) 
    {
            setPsmlId(Integer.parseInt(key));
        }

  
    /**
     * returns an id that differentiates this object from others
     * of its class.
     */
    public ObjectKey getPrimaryKey()
    {
          return SimpleKey.keyFor(getPsmlId());
      }
 

    /**
     * Makes a copy of this object.
     * It creates a new object filling in the simple attributes.
       * It then fills all the association collections.
       */
      public JetspeedUserProfile copy() throws TorqueException
    {
        JetspeedUserProfile copyObj = new JetspeedUserProfile();
            copyObj.setPsmlId(psmlId);
          copyObj.setUserName(userName);
          copyObj.setMediaType(mediaType);
          copyObj.setLanguage(language);
          copyObj.setCountry(country);
          copyObj.setPage(page);
          copyObj.setProfile(profile);
  
                      copyObj.setPsmlId(0);
                                                
                return copyObj;
    }

    /**
     * returns a peer instance associated with this om.  Since Peer classes
     * are not to have any instance attributes, this method returns the
     * same instance for all member of this class. The method could therefore
     * be static, but this would prevent one from overriding the behavior.
     */
    public JetspeedUserProfilePeer getPeer()
    {
        return peer;
    }
}
