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
    /**
     * Debug flag for the logger.
     */
    public static final boolean DEBUG = false;
    /**
     * Logger for the class.
     */
    private static final Logger logger =
            Logger.getLogger(DependencyShot.class.getSimpleName());
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
        long start = System.currentTimeMillis();
        PluginsLoader.getInstance().loadFirstPlugins();
        List<ConfigurationHandler> handlers = (List<ConfigurationHandler>)
                PluginsLoader.getInstance().getConfigurationHandlers();
        try {
            if (handlers.size() > 0 && handlers.get(0).isAutoEnabled()) {
                return handlers.get(0).getInjector(stage);
            }
            return InjectorBuilder.makeInjector(binders, stage);
        } finally {
            if (handlers.size() > 1) {
                logger.warning("There are more than one configurator plugin in the classpath.");
                logger.warning("The plugin : "
                        + handlers.get(0).getClass().getSimpleName()
                        + " is used for this session.");
            }
            if (DEBUG) {
                logger.info("Time elapsed for bootstrapping : "
                        + (System.currentTimeMillis() - start)
                        + " ms.");
            }
        }
    }
    /**
     * Allow user to manipulate a configurator extension point.
     *
     * @return a SPI configurationHandler.
     */
    public static ConfigurationHandler getSpecificConfigurator() {
        PluginsLoader.getInstance().loadFirstPlugins();
        List<ConfigurationHandler> handlers = (List<ConfigurationHandler>)
                PluginsLoader.getInstance().getConfigurationHandlers();
        if (handlers.size() > 1) {
            logger.warning("There are more than one configurator plugin in the classpath.");
            logger.warning("The plugin : "
                    + handlers.get(0).getClass().getSimpleName()
                    + " is used for this session.");
        }
        if (handlers.size() > 0) {
            return handlers.get(0);
        }
        return null;
    }
}
