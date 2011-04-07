/*
 *  Copyright 2011 mathieuancelin.
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

/**
 *
 * @author mathieuancelin
 */
public interface ServiceRegistry {

    public void addServiceListener(Class<?> listener);

    public void removeServiceListener(Class<?> listener);

    public <T> ServiceRegistration registerService(Class<T> clazz,  Class<?> service);

    public <T> ServiceRegistration registerService(Class<T> clazz,  T service);

    public <T> ServiceRegistration registerService(Class<T>[] clazzes,  Class<?> service);

    public <T> ServiceRegistration registerService(Class<T>[] clazzes,  T service);

    public <T> T getService(Class<T> contract);

    public <T> Iterable<T> getServices(Class<T> contract);

//    public <T> boolean ungetService(T service);
//
//    public <T> boolean ungetServices(Collection<T> services);
//
//    public Map<Class<?>, Collection<Class<?>>> getAvailableServices();

    public static interface ServiceRegistration {
        void unregister();
    }
}
