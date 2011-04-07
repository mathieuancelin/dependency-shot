/*
 *  Copyright 2010 mathieuancelin.
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

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;

/**
 *
 * @author Mathieu ANCELIN
 */
public class DynamicProxy<T> implements MethodFilter, MethodHandler, InvocationHandler {

    private final Class<T> from;

    private final InjectionPoint point;

    private final InjectorImpl injector;

    private final ServiceRegistryImpl registry;

    private Object actualService;

    public DynamicProxy(
            Class<T> from, InjectionPoint point,
            InjectorImpl injector, ServiceRegistryImpl registry) {
        this.from = from;
        this.injector= injector;
        this.registry = registry;
        this.point = point;
    }

    public Class<T> getFrom() {
        return from;
    }

    public InjectionPoint getPoint() {
        return point;
    }

    public InjectorImpl getInjector() {
        return injector;
    }

    @Override
    public boolean isHandled(Method method) {
        return true;
    }

    @Override
    public Object invoke(Object self, Method method, Method proceed, Object[] args) throws Throwable {
        actualService = registry.getService(from);
        return method.invoke(actualService, args);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        actualService = registry.getService(from);
        return method.invoke(actualService, args);
    }
}
