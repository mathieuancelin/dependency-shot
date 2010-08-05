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

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.ImplementationValidator;
import cx.ath.mancel01.dependencyshot.spi.InstanceHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptors;

/**
 * Handler for AOP manipulations.
 * 
 * @author mathieu
 */
public class AopInstanceHandler extends InstanceHandler{

    private static final Logger logger = Logger.getLogger(AopInstanceHandler.class.getSimpleName());

    private List<DSInterceptor> managedInterceptors = new ArrayList<DSInterceptor>();

    @Override
    public final <T extends ImplementationValidator> T getValidator() {
        return (T) new AOPValidator();
    }

    @Override
    public final boolean isInstanceValid(Object instance) {
        return new AOPValidator().isValid(instance);
    }

    @Override
    public final Object manipulateInstance(Object instance, Class interf, InjectorImpl injector, InjectionPoint point) {
        return scannInterceptorsAnnotations(instance, interf, injector);
    }

    /**
     * Check if the object is interceptable.
     * If it is, this method add interceptors chain on it.
     *
     * @param obj the concerned object.
     * @param interfaceClazz the interface.
     * @return the object with interceptor handler (if annotations are presents)
     */
    private Object scannInterceptorsAnnotations(
            final Object obj,
            final Class interfaceClazz,
            InjectorImpl injector) {
        AOPValidator validator = new AOPValidator();
        if (!validator.isValid(obj)) {
            return obj;
        }
        Class clazz = obj.getClass();
        Object ret = obj;
        if (interfaceClazz.isAnnotationPresent(Interceptors.class)) {
            findAroundInvoke(interfaceClazz, injector);
        }
        if (clazz.isAnnotationPresent(Interceptors.class)) {
            findAroundInvoke(clazz, injector);
        }
        for (Method m : interfaceClazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Interceptors.class)) {
                findAroundInvoke(m, injector);
            }
        }
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Interceptors.class)) {
                findAroundInvoke(m, injector);
            }
        }
        if (managedInterceptors.size() > 0) {
            managedInterceptors.add(new FinalInterceptor());
            DSInterceptor[] interceptors =
                    new DSInterceptor[managedInterceptors.size()];
            int i = 0;
            for (DSInterceptor o : managedInterceptors) {
                interceptors[i] = o;
                i++;
            }
            ret = Weaver.getInstance().weaveObject(interfaceClazz, obj, interceptors);
        }
        managedInterceptors.clear();
        return ret;
    }

    /**
     * Check for @AroundInvoke on a class.
     * @param clazz the checked class.
     */
    private void findAroundInvoke(final Class clazz, InjectorImpl injector) {
        Interceptors inter =
                (Interceptors) clazz.getAnnotation(Interceptors.class);
        Object interceptorInstance = null;
        for (Class c : inter.value()) {
            try {
                try {
                    interceptorInstance = injector.getInstance(c);//TODO : find better way to do it
                } catch (Exception e) {
                    interceptorInstance = c.newInstance();
                }
                for (Method m : c.getDeclaredMethods()) {
                    if (m.isAnnotationPresent(AroundInvoke.class)) {
                        managedInterceptors.add(
                                new UserInterceptor(m, interceptorInstance));
                    }
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Check for @AroundInvoke on a method.
     * @param method the checked method.
     */
    private void findAroundInvoke(final Method method, InjectorImpl injector) {
        Interceptors inter = (Interceptors) method.getAnnotation(Interceptors.class);
        Object interceptorInstance = null;
        for (Class c : inter.value()) {
            try {
                try {
                    interceptorInstance = injector.getInstance(c);//TODO : find better way to do it
                } catch (Exception e) {
                    interceptorInstance = c.newInstance();
                }
                for (Method m : c.getDeclaredMethods()) {
                    if (m.isAnnotationPresent(AroundInvoke.class)) {
                        UserInterceptor interceptorTmp =
                                new UserInterceptor(m, interceptorInstance);
                        interceptorTmp.setAnnotatedMethod(method);
                        managedInterceptors.add(interceptorTmp);
                    }
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
