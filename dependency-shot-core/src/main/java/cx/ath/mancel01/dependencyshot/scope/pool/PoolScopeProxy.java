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

package cx.ath.mancel01.dependencyshot.scope.pool;

import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * @author mathieuancelin
 */
public class PoolScopeProxy implements InvocationHandler {

    private final ThreadLocal<Map<Class<?>, Object>> threadLocalinstances;

    private final Class clazz;

    private final InjectorImpl injector;

    public <T> PoolScopeProxy(ThreadLocal<Map<Class<?>, Object>> threadLocalinstances, Class<? extends T> clazz, InjectorImpl injector) {
        this.threadLocalinstances = threadLocalinstances;
        this.clazz = clazz;
        this.injector = injector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Map<Class<?>, Object> scope = threadLocalinstances.get();
        Object result = clazz.cast(scope.get(clazz));
        if (result == null) {
            result = injector.createInstance(clazz);
            scope.put(clazz, result);
        }
        return method.invoke(result, args);
    }
}
