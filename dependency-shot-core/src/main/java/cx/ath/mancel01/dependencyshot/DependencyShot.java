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

package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.injection.InjectorBuilder;
import cx.ath.mancel01.dependencyshot.spi.ConfigurationHandler;
import cx.ath.mancel01.dependencyshot.spi.PluginsLoader;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * This class is the entry point of the framework.
 * It handle configuration binder and return injector
 * for your own apps.
 *
 * @author Mathieu ANCELIN
 */
public final class DependencyShot {

    public static final boolean DEBUG = false;
    
    private static Logger logger = Logger.getLogger(DependencyShot.class.getSimpleName());
    /**
     * Private constructor cause it's a utility class.
     */
    private DependencyShot() {

    }
    /**
     * Get an injector from configuration binders.
     * @param binders configuration binders.
     * @return the injector.
     */
    public static InjectorImpl getInjector(final DSBinder... binders) {
        return getInjector(Arrays.asList(binders), null);
    }
    /**
     * Get an injector from configuration binders.
     * @param binders configuration binders.
     * @return the injector.
     */
    public static InjectorImpl getInjector(Stage stage, final DSBinder... binders) {
        return getInjector(Arrays.asList(binders), stage);
    }
    /**
     * Get an injector from configuration binders.
     * @param binders binders configuration binders
     * @return the injector.
     */
    public static InjectorImpl getInjector(final Iterable<? extends DSBinder> binders, Stage stage) {
        PluginsLoader.getInstance().loadFirstPlugins();
        long start = System.currentTimeMillis();
        try {
            return InjectorBuilder.makeInjector(binders, stage);
        } finally {
            if (DEBUG) {
                logger.info("Time elapsed for bootstrapping : " + (System.currentTimeMillis() - start) + " ms.");
            }
        }
    }

    public static ConfigurationHandler getSpecificConfigurator() {
        PluginsLoader.getInstance().loadFirstPlugins();
        List<ConfigurationHandler> handlers = (List<ConfigurationHandler>) PluginsLoader.getInstance().getConfigurationHandlers();
        if (handlers.size() > 0)
            return handlers.get(0);
        return null;
    }
}
