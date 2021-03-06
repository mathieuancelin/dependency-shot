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

import cx.ath.mancel01.dependencyshot.dynamic.integration.Registrations;
import cx.ath.mancel01.dependencyshot.dynamic.integration.Service;
import cx.ath.mancel01.dependencyshot.dynamic.integration.Services;
import cx.ath.mancel01.dependencyshot.dynamic.integration.ServicesProducer.ContextProvider;
import cx.ath.mancel01.dependencyshot.dynamic.integration.ServicesProducer.OSGiServiceProvider;
import cx.ath.mancel01.dependencyshot.dynamic.integration.ServicesProducer.OSGiServicesProvider;
import cx.ath.mancel01.dependencyshot.dynamic.integration.ServicesProducer.RegistrationsProvider;
import cx.ath.mancel01.dependencyshot.dynamic.registry.ServiceRegistry;
import cx.ath.mancel01.dependencyshot.dynamic.registry.ServiceRegistryProvider;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.graph.BindingBuilder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.BindingsProvider;
import java.util.ArrayList;
import java.util.Collection;
import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN
 */
public class DynamicProvider extends BindingsProvider {

    @Override
    public Collection<Binding> getProvidedBindings(InjectorImpl injector) {

        ArrayList<Binding> bindings = new ArrayList<Binding>();

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(ServiceRegistry.class)
                .providedBy(new ServiceRegistryProvider())
                .build());
        
        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(ServiceRegistryProvider.OSGiEnvHolder.class)
                .build());

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(BundleContext.class)
                .providedBy(new ContextProvider())
                .build());

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(Services.class)
                .providedBy(new OSGiServicesProvider())
                .build());

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(Service.class)
                .providedBy(new OSGiServiceProvider())
                .build());

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(Registrations.class)
                .providedBy(new RegistrationsProvider())
                .build());

        return bindings;
    }

}
