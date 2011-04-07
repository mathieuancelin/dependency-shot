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
package cx.ath.mancel01.dependencyshot.dynamic;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.ConfigurationHandler;
import javax.inject.Inject;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class OSGiConfigurator extends ConfigurationHandler {

    private BundleContext context;
    private Binder binder;

    @Inject @OSGi
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
        injector.injectInstance(this);
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

    public OSGiConfigurator withBinder(Binder binder) {
        this.binder = binder;
        return this;
    }

    public OSGiConfigurator context(BundleContext context) {
        this.context = context;
        return this;
    }
}
