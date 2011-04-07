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
package cx.ath.mancel01.dependencyshot.dynamic.config;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.dynamic.registry.OSGiServiceRegistryImpl;
import cx.ath.mancel01.dependencyshot.dynamic.registry.ServiceRegistry;
import cx.ath.mancel01.dependencyshot.dynamic.registry.ServiceRegistryProvider;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.ConfigurationHandler;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class OSGiConfigurator extends ConfigurationHandler {

    private BundleContext context;
    private Binder binder;
    private ServiceRegistry registry;

    @Override
    public InjectorImpl getInjector(Stage stage) {
        InjectorImpl injector = null;
        if (context == null) {
            throw new IllegalStateException("You must set the current bundle context.");
        }
        if (binder == null) {
            // TODO :
            injector = DependencyShot.getInjector();
        } else {
            injector = DependencyShot.getInjector(binder);
        }
        ServiceRegistryProvider.OSGiEnvHolder holder = 
                injector.getInstance(ServiceRegistryProvider.OSGiEnvHolder.class);
        holder.setOsgi(true);
        registry = injector.getInstance(ServiceRegistry.class);
        if (registry == null) {
            throw new IllegalStateException("Injection for bundle context failed.");
        }
        ((OSGiServiceRegistryImpl) registry).setContext(context);
        return injector;
    }

    @Override
    public boolean isAutoEnabled() {
        return false;
    }

    public OSGiConfigurator withBinder(DSBinder binder) {
        this.binder = (Binder) binder;
        return this;
    }

    public OSGiConfigurator context(BundleContext context) {
        this.context = context;
        return this;
    }
}
