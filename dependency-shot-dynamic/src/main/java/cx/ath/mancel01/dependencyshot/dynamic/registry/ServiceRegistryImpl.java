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
import cx.ath.mancel01.dependencyshot.dynamic.Dynamic;
import cx.ath.mancel01.dependencyshot.event.EventManagerImpl;
import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author mathieu
 */
@Singleton
public class ServiceRegistryImpl implements ServiceRegistry {

    @Inject
    private DSInjector injector;

    @Inject
    private EventManager eventManager;

    private ConcurrentHashMap<Class<?>, Collection<Class<?>>> services =
            new ConcurrentHashMap<Class<?>, Collection<Class<?>>>();

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Class<?> clazz : services.keySet()) {
            builder.append(clazz.getName());
            ArrayList<Class<?>> classes = (ArrayList<Class<?>>) services.get(clazz);
            for (Class<?> service : classes) {
                builder.append("\n      => ");
                builder.append(service.getName());
            }
            builder.append("\n\n");
        }
        return builder.toString();
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
//        if(!service.isAnnotationPresent(Dynamic.class)) {
//            throw new DSIllegalStateException("You can't register non dynamic implementation for a service");
//        }
        services.putIfAbsent(clazz, new ArrayList<Class<?>>());
        ArrayList<Class<?>> classes = (ArrayList<Class<?>>) services.get(clazz);
        if (!classes.contains(service)) {
            synchronized(classes) {
                classes.add(service);
            }
        }
        List<Class<?>> itfs = new ArrayList<Class<?>>();
        itfs.add(clazz);
        return new ServiceRegistrationImpl(itfs, service);
    }

    @Override
    public <T> ServiceRegistration registerService(Class<T> clazz, T service) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> ServiceRegistration registerService(Class<T>[] clazzes, Class<?> service) {
        for (Class<T> clazz : clazzes) {
            registerService(clazz, service);
        }
        return new ServiceRegistrationImpl(Arrays.asList(clazzes), service);
    }

    @Override
    public <T> ServiceRegistration registerService(Class<T>[] clazzes, T service) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <T> T getService(Class<T> contract) {
        return (T) ((InjectorImpl)injector)
            .getUnscopedInstance(((ArrayList<Class<?>>) services.get(contract)).get(0));
    }

    @Override
    public <T> Iterable<T> getServices(Class<T> contract) {
        Collection<T> objects = new ArrayList<T>();
        for (Class<?> clazz : services.get(contract)) {
            objects.add((T) ((InjectorImpl)injector).getUnscopedInstance(clazz));
        }
        return objects;
    }

    public Map<Class<?>, Collection<Class<?>>> getAvailableServices() {
        return services;
    }

    public void swap(Class<?> to) {
        if(!to.isAnnotationPresent(Dynamic.class)) {
            throw new DSIllegalStateException("You can't register non dynamic implementation for a service");
        }
        for (Class from : to.getInterfaces()) {
            ArrayList<Class<?>> classes = (ArrayList<Class<?>>) services.get(from);
            if (classes.contains(to)) {
                int index = classes.indexOf(to);
                Class<?> old = classes.set(0, to);
                classes.set(index, old);
            } else {
                registerServiceAndSwap(from, to);
            }
        }
    }

    public void registerServiceAndSwap(Class<?> from, Class<?> to) {
//        if(!to.isAnnotationPresent(Dynamic.class)) {
//            throw new DSIllegalStateException("You can't register non dynamic implementation for a service");
//        }
        services.putIfAbsent(from, new ArrayList<Class<?>>());
        ArrayList<Class<?>> classes = (ArrayList<Class<?>>) services.get(from);
        if (!classes.contains(to)) {
            synchronized(classes) {
                classes.add(0, to);
            }
        }
    }

    public Class<?> getContract(Class<?> from) {
        return ((ArrayList<Class<?>>) services.get(from)).get(0);
    }

    public Collection<Class<?>> multipleContractsLookup(Class<?> from) {
        return services.get(from);
    }

    private class ServiceRegistrationImpl<T> implements ServiceRegistration {

        final List<Class<T>> classes;
        final Class<? extends T> to;

        public ServiceRegistrationImpl(List<Class<T>> classes, Class<? extends T> to) {
            this.classes = classes;
            this.to = to;
        }

        @Override
        public void unregister() {
            for (Class from : classes) {
                services.get(from).remove(to);
            }
        }
    }
}
