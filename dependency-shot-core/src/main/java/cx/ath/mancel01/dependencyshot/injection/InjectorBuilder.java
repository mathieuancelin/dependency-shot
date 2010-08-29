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
package cx.ath.mancel01.dependencyshot.injection;

import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.spi.PluginsLoader;

/**
 * Utility classthat create injector.
 * 
 * @author Mathieu ANCELIN
 */
public final class InjectorBuilder { 
                                     
    /**
     * Private constructor.
     */
    private InjectorBuilder() { }
    /**
     * Return an injector.
     * @param binders the binders.
     * @return the configured injector.
     */
    public static InjectorImpl makeInjector(final Iterable<? extends Binder> binders, PluginsLoader loader, Stage stage) {
        InjectorImpl injector = new InjectorImpl(loader, stage);
        //injector.registerShutdownHook();
        injector.resetBinders();
        for (Binder binder : binders) {
            injector.addBinder(binder);
        }
        injector.configureBinders();
        return injector;
    }
    /**
     * Create an injector based on a binder.
     * @param binder the only binder.
     * @return the configured injector.
     */
    public static InjectorImpl makeInjector(final Binder binder, PluginsLoader loader, Stage stage) {
        InjectorImpl injector = new InjectorImpl(loader, stage);
        //injector.registerShutdownHook();
        injector.resetBinders();
        injector.addBinder(binder);
        injector.configureBinders();
        return injector;
    }
}
