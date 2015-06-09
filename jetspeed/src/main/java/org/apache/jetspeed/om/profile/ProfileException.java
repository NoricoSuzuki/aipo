/*
 * Copyright 2000-2001,2004 The Apache Software Foundation.
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

package org.apache.jetspeed.om.profile;

import org.apache.jetspeed.util.JetspeedException;


/**
Occurs when anything unexpected happens within Jetspeed with a Profile.

@author <a href="mailto:david@bluesunrise.com">David Sean Taylor</a>
@version $Id: ProfileException.java,v 1.3 2004/02/23 03:05:01 jford Exp $
*/

public class ProfileException extends JetspeedException {

    public static final String PROFILE_FAILED_STORE
        = "The profile failed to store.";

    public ProfileException() {
        super();
    }

    public ProfileException( String message ) {
        super( message );
    }

}
