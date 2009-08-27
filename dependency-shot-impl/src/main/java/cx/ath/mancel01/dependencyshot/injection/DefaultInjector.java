/*
 *  Copyright 2009 Mathieu ANCELIN.
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

import cx.ath.mancel01.dependencyshot.api.DSInjector;

/**
 * An injector based on the default xml configuration file.
 * 
 * @author Mathieu ANCELIN
 */
public final class DefaultInjector implements DSInjector {

    /**
     * The unique instance of the class.
     */
    private static DefaultInjector instance = null;
    /**
     * The default name of the default configuration file.
     */
    private static final String DEFAULT_CONF_FILE_NAME = "bindings.xml";
    /**
     * The default path of the default configuration file.
     */
    private static final String DEFAULT_CONF_FILE_PATH = "META-INF";

    static {
        // get the default configuration file.
        DefaultInjector.class.getResourceAsStream(DEFAULT_CONF_FILE_PATH + "/" + DEFAULT_CONF_FILE_NAME);
    }

    /**
     * The private constructor of the singleton.
     */
    private DefaultInjector() {
        configureBindings();
    }

    /**
     * The accessor for the unique instance of the singleton.
     * @return the unique instance of the singleton.
     */
    public static synchronized DefaultInjector getInstance() {
        if (instance == null) {
            instance = new DefaultInjector();
        }
        return instance;
    }

    /**
     * Configure the bindings of the injector from the configuration file.
     */
    private void configureBindings() {
    }
    
    /**
     * reload the configuration file.
     */
    public void reloadBindingsConfiguration() {
        configureBindings();
    }
    /**
     * 
     * @param <T>
     * @param type
     * @return
     */
    @Override
    public <T> T getObjectInstance(Class<T> type) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Object injectFields(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     *
     * @param obj
     * @return
     */
    @Override
    public Object injectInstance(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * 
     * @param obj
     * @return
     */
    @Override
    public Object injectMembers(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
