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

package cx.ath.mancel01.dependencyshot.aop.v2;

import java.lang.reflect.Method;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javassist.util.proxy.MethodFilter;
import javassist.util.proxy.MethodHandler;
import org.aopalliance.intercept.MethodInterceptor;

/**
 *
 * @author Mathieu ANCELIN
 */
public class MethodInvocationHandler<T> implements MethodFilter, MethodHandler {

    private final List<MethodInterceptor> interceptors;

    private final Class<T> from;

    private final Class<? extends T> to;

    private final Object interceptedBean;

    private final ConcurrentHashMap<Method, Boolean> areMethodHandled =
            new ConcurrentHashMap<Method, Boolean>();

    public MethodInvocationHandler(List<MethodInterceptor> interceptors,
            Class<T> from, Class<? extends T> to, Object interceptedBean) {
        this.interceptors = interceptors;
        this.from = from;
        this.to = to;
        this.interceptedBean = interceptedBean;
    }

    @Override
    public boolean isHandled(Method method) {
        if (areMethodHandled.containsKey(method)) {
            return areMethodHandled.get(method);
        } else {
            boolean handled = !method.isAnnotationPresent(ExcludeInterceptors.class);
            if (from.isInterface()) {
                Method impl = null;
                try {
                    impl = to.getMethod(method.getName(), method.getParameterTypes());
                } catch (Exception e) {}
                if (impl != null) {
                    if (handled) {
                        handled = !impl.isAnnotationPresent(ExcludeInterceptors.class);
                    }
                }
            }
            areMethodHandled.putIfAbsent(method, handled);
            return handled;
        }
    }

    @Override
    public Object invoke(Object self, Method method, Method procced, Object[] args) throws Throwable {
        MethodJoinpoint joinpoint = new MethodJoinpoint(method, args,
                interceptors.iterator(), interceptedBean, this);
        return joinpoint.proceed();
    }
}
