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
package cx.ath.mancel01.dependencyshot.injection.handlers;

import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.util.ReflectionUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Utility class that handle injection on the methods of a class.
 *
 * @author Mathieu ANCELIN
 */
public final class MethodHandler {

    private static final Logger logger = Logger.getLogger(MethodHandler.class.getSimpleName());

    /**
     * Constructor.
     */
    public MethodHandler() {
    }

    /**
     * Inject all injectable methods of an object.
     * 
     * @param <T> type of the injected object.
     * @param instance instance of the injected object.
     * @param c class of the injected object.
     * @param maybeOverrides list of potential overridden methods.
     * @param staticInjection can we inject static fields.
     * @param injector the concerned injector.
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public <T> void methodsInjection(T instance, Class<?> c,
            List<Method> maybeOverrides,
            boolean staticInjection,
            InjectorImpl injector) throws IllegalAccessException,
            InvocationTargetException {

        Method[] methodsOfTheClass = c.getDeclaredMethods();
        // for each method of the class
        for (Method method : methodsOfTheClass) {
            Inject annotation = method.getAnnotation(Inject.class);
            // check if method is injectable and if you can inject static methods and if method is overriden
            if (annotation != null && (staticInjection == Modifier.isStatic(method.getModifiers()))
                    && !ReflectionUtil.isOverridden(method, maybeOverrides)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Type[] genericParameterTypes = method.getGenericParameterTypes();
                Annotation[][] parameterAnnotations = method.getParameterAnnotations();
                Object[] parameters = new Object[parameterTypes.length];
                for (int j = 0; j < parameterTypes.length; j++) {
                    // inject parameters instance (simple instances or provided)
                    parameters[j] = injector.getProviderOrInstance(parameterTypes[j],
                            genericParameterTypes[j],
                            parameterAnnotations[j],
                            method);
                }
                ReflectionUtil.invokeMethod(method, instance, parameters);
            }
        }
    }
}
