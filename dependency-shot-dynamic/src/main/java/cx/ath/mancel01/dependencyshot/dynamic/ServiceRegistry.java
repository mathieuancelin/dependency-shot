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

package cx.ath.mancel01.dependencyshot.dynamic;

import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author mathieu
 */
public class ServiceRegistry {

    private final static ServiceRegistry INSTANCE =
            new ServiceRegistry();

    private ConcurrentHashMap<Class<?>, ArrayList<Class<?>>> services =
            new ConcurrentHashMap<Class<?>, ArrayList<Class<?>>>();

    private ServiceRegistry() {
    }

    public static ServiceRegistry getInstance() {
        return INSTANCE;
    }

    public void registerService(Class<?> from, Class<?> to) {
        if(!to.isAnnotationPresent(Dynamic.class)) {
            throw new DSIllegalStateException("You can't register non dynamic implementation for a service");
        }
        services.putIfAbsent(from, new ArrayList<Class<?>>());
        ArrayList<Class<?>> classes = services.get(from);
        if (!classes.contains(to)) {
            synchronized(classes) {
                classes.add(to);
            }
        }
    }

    public void swap(Class<?> to) {
        if(!to.isAnnotationPresent(Dynamic.class)) {
            throw new DSIllegalStateException("You can't register non dynamic implementation for a service");
        }
        for (Class from : to.getInterfaces()) {
            ArrayList<Class<?>> classes = services.get(from);
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
        if(!to.isAnnotationPresent(Dynamic.class)) {
            throw new DSIllegalStateException("You can't register non dynamic implementation for a service");
        }
        services.putIfAbsent(from, new ArrayList<Class<?>>());
        ArrayList<Class<?>> classes = services.get(from);
        if (!classes.contains(to)) {
            synchronized(classes) {
                classes.add(0, to);
            }
        }
    }

    public void unregisterService(Class<?> to) {
        for (Class from : to.getInterfaces()) {
            services.get(from).remove(to);
        }
    }

    public Class<?> lookup(Class<?> from) {
        return services.get(from).get(0);
    }
    
    public Collection<Class<?>> multipleLookup(Class<?> from) {
        return services.get(from);
    }

    public Map<Class<?>, ArrayList<Class<?>>> getServices() {
        return services;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (Class<?> clazz : services.keySet()) {
            builder.append(clazz.getName());
            List<Class<?>> classes = services.get(clazz);
            for (Class<?> service : classes) {
                builder.append("\n      => ");
                builder.append(service.getName());
            }
            builder.append("\n\n");
        }
        return builder.toString();
    }
}
