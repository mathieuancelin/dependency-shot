/*
 *  Copyright 2009-2010 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependencyshot.spi;

import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;

/**
 *
 * @author Mathieu ANCELIN
 */
public abstract class ConfigurationHandler {

    /**
     * Get an injector from configuration binders.
     * @param binders configuration binders.
     * @return the configurated injector.
     */
    public InjectorImpl getInjector(final Object... params) {
        return getInjector(null, params);
    }
    /**
     * Get an injector from configuration binders.
     * @param binders configuration binders.
     * @return the configurated injector.
     */
    public abstract InjectorImpl getInjector(Stage stage, final Object... params);

    public abstract Object getDelegate();
    
}
