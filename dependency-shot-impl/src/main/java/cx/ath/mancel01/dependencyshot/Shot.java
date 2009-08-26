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

package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.injection.DefaultInjector;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.injection.InjectorMaker;
import java.util.Arrays;

/**
 * This class is the entry point of the framework.
 * It handle configuration binder and return injector
 * for your own apps.
 * 
 * @author mathieuancelin
 */
public final class Shot {
    /**
     * Private constructor cause it's a utility class.
     */
    private Shot() {

    }
    /**
     * Get an injector from a specific configuration file.
     * @param xmlBindingFile path to the specific configuration file.
     * @return the injector.
     */
    public static InjectorImpl getInjector(final String xmlBindingFile) {
        return InjectorMaker.makeInjector(xmlBindingFile); // TODO xml configuration
    }
    /**
     * Get an injector from configuration binders.
     * @param binders configuration binders.
     * @return the injector.
     */
    public static InjectorImpl getInjector(final DSBinder... binders) {
        return getInjector(Arrays.asList(binders));
    }
    /**
     * Get an injector from configuration binders.
     * @param binders binders configuration binders
     * @return the injector.
     */
    public static InjectorImpl getInjector(final Iterable<? extends DSBinder> binders) {
        return InjectorMaker.makeInjector(binders);
    }
    /**
     * Get the default injector configured with the bindings.xml file
     * present in the META-INF directory.
     * @return the injector.
     */
    public static DefaultInjector getDefaultInjector() {
        return DefaultInjector.getInstance();
    }
}
