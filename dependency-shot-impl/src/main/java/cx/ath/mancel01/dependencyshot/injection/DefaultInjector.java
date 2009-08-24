/*
 *  Copyright 2009 mathieuancelin.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package cx.ath.mancel01.dependencyshot.injection;


/**
 *
 * @author mathieuancelin
 */
public class DefaultInjector {

    /**
     * The unique instance of the class
     **/
    private static DefaultInjector INSTANCE = null;

    private static final String DEFAULT_CONF_FILE_NAME = "bindings.xml";

    private static final String DEFAULT_CONF_FILE_PATH = "META-INF";
    
    static {
        DefaultInjector.class.getResourceAsStream(DEFAULT_CONF_FILE_PATH 
                + "/"
                + DEFAULT_CONF_FILE_NAME);
    }

    /**
     * The private constructor of the singleton
     **/
    private DefaultInjector() {
        configureBindings();
    }

    /**
     * The accessor for the unique instance of the singleton
     **/
    public static synchronized DefaultInjector getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new DefaultInjector();
        }
        return INSTANCE;
    }

    private void configureBindings(){
        
    }

    public void reloadBindingsConfiguration(){
        configureBindings();
    }
}
