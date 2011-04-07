/*
 *  Copyright 2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.dynamic.registry;

import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.event.EventManager;
import cx.ath.mancel01.dependencyshot.event.EventManagerImpl;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;

/**
 *
 * @author mathieu
 */
@Singleton
public class OSGiServiceRegistryImpl implements ServiceRegistry, ServiceListener {

    @Inject
    private DSInjector injector;

    @Inject
    private EventManager eventManager;

    private BundleContext context;

    public void setContext(BundleContext context) {
        this.context = context;
        context.addServiceListener(this);
    }

    @Override
    public void addServiceListener(Class<?> listener) {
        ((EventManagerImpl) eventManager).registerListener(listener);
    }

    @Override
    public void removeServiceListener(Class<?> listener) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> ServiceRegistration registerService(Class<T> clazz, Class<?> service) {
        return new ServiceRegistrationImpl(
                context.registerService(clazz.getName(), injector.getInstance(service), null));
    }

    @Override
    public <T> ServiceRegistration registerService(Class<T> clazz, T service) {
        return new ServiceRegistrationImpl(
                context.registerService(clazz.getName(), service, null));
    }

    @Override
    public <T> ServiceRegistration registerService(Class<T>[] clazzes, Class<?> service) {
        List<String> names = new ArrayList<String>();
        for (Class<T> clazz : clazzes) {
            names.add(clazz.getName());
        }
        return new ServiceRegistrationImpl(
                context.registerService(names.toArray(new String[] {}), injector.getInstance(service), null));
    }

    @Override
    public <T> ServiceRegistration registerService(Class<T>[] clazzes, T service) {
         List<String> names = new ArrayList<String>();
        for (Class<T> clazz : clazzes) {
            names.add(clazz.getName());
        }
        return new ServiceRegistrationImpl(
                context.registerService(names.toArray(new String[] {}), service, null));
    }

    @Override
    public <T> T getService(Class<T> contract) {
        return (T) context.getService(context.getServiceReference(contract.getName()));
    }

    @Override
    public <T> Iterable<T> getServices(Class<T> contract) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void serviceChanged(ServiceEvent se) {
        injector.fire(se);
    }

    private class ServiceRegistrationImpl<T> implements ServiceRegistration {

        final org.osgi.framework.ServiceRegistration reg;

        public ServiceRegistrationImpl(org.osgi.framework.ServiceRegistration reg) {
            this.reg = reg;
        }

        @Override
        public void unregister() {
            reg.unregister();
        }
    }
}
