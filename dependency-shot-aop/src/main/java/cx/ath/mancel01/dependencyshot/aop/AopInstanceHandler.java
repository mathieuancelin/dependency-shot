/*
 *  Copyright 2009-2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.aop.annotation.Interceptors;
import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.ImplementationValidator;
import cx.ath.mancel01.dependencyshot.spi.InstanceHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;

/**
 * Handler for AOP manipulations.
 * 
 * @author mathieu
 */
public class AopInstanceHandler extends InstanceHandler{

    @Override
    public final <T extends ImplementationValidator> T getValidator() {
        return (T) new ImplementationValidator() {
            @Override
            public boolean isValid(Object o) {
                return true;
            }
        };
    }

    @Override
    public final boolean isInstanceValid(Object instance) {
        return true;
    }

    @Override
    public final Object manipulateInstance(Object instance, Class interf, InjectorImpl injector, InjectionPoint point) {
        List<MethodInterceptorWrapper> interceptors = new ArrayList<MethodInterceptorWrapper>();
        Class clazz = instance.getClass();
        applyAspect(clazz, interceptors, injector);
        for (Binder binder : injector.getBinders()) {
            if (binder instanceof AOPBinder) {
                interceptors.addAll(((AOPBinder) binder).getInterceptors(clazz, injector));
            }
        }
        MethodInvocationHandler handler = new MethodInvocationHandler(
                interceptors, interf, clazz, instance);
        return ProxyHelper.createProxy(instance, handler);
    }

    private void applyAspect(Class clazz, 
            List<MethodInterceptorWrapper> interceptors, InjectorImpl injector) {
        if (clazz.isAnnotationPresent(Interceptors.class)) {
            Interceptors annotationInterceptors = ((Interceptors) clazz.getAnnotation(Interceptors.class));
            for (Class<? extends MethodInterceptor> interceptor 
                    : annotationInterceptors.value()) {
                interceptors.add(
                    new MethodInterceptorWrapper(
                        injector.getInstance(interceptor)
                    )
                );
            }
        }
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Interceptors.class)) {
                Interceptors annotationInterceptors =
                        ((Interceptors) method.getAnnotation(Interceptors.class));
                for (Class<? extends MethodInterceptor> interceptor :
                        annotationInterceptors.value()) {
                    MethodInterceptorWrapper wrapper = new MethodInterceptorWrapper(
                        injector.getInstance(interceptor)
                    );
                    wrapper.addInterceptedMethod(method);
                    interceptors.add(wrapper);
                }
            }
        }
    }
}
