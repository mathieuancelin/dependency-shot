/*
 *  Copyright 2009-2010 Mathieu ANCELIN
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

package play.modules.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.DSInjector;

/**
 * Util class to retrieve the current app injector in a non
 * managed class.
 *
 * @author Mathieu ANCELIN
 */
public class InjectorLocator {

    private static final InjectorLocator INSTANCE = new InjectorLocator();

    private DSInjector injector;

    private InjectorLocator() {
        // left empty
    }

    public static InjectorLocator getInstance() {
        return INSTANCE;
    }

    public DSInjector getInjector() {
        return injector;
    }

    void setInjector(DSInjector injector) {
        this.injector = injector;
    }

}
