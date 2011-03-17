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

import cx.ath.mancel01.dependencyshot.exceptions.DSException;
import cx.ath.mancel01.dependencyshot.exceptions.ExceptionManager;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Utility class used to manipulate class constructor and create instances.
 * 
 * @author Mathieu ANCELIN
 */
public final class ConstructorHandler {

    private static final Logger logger = Logger.getLogger(ConstructorHandler.class.getSimpleName());

    /**
     * Constructor.
     */
    public ConstructorHandler() {
    }

    /**
     * Return an instance of an object by constructor invocation.
     *
     * @param <T> type of the class to construct
     * @param c class to construct
     * @param injector the concerned injector
     * @return new instance of c
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public <T> T getConstructedInstance(Class<T> c, InjectorImpl injector)
            throws InstantiationException,
            IllegalAccessException,
            InvocationTargetException {
        // get constructors defined in the class c
        Constructor<?>[] constructorsOfTheClass = c.getDeclaredConstructors();
        for (Constructor<?> constructor : constructorsOfTheClass) {
            Inject annotation = constructor.getAnnotation(Inject.class);
            Class<?>[] parameterTypes = constructor.getParameterTypes();
            Type[] genericParameterTypes = constructor.getGenericParameterTypes();
            boolean isDefaultConstructor = (constructorsOfTheClass.length == 1
                    && parameterTypes.length == 0
                    && Modifier.isPublic(constructor.getModifiers()));
            // check if constructor is injectable or if it's a default constructor
            if (annotation != null || isDefaultConstructor) {
                Object[] arguments = new Object[parameterTypes.length];
                Annotation[][] parameterAnnotations = constructor.getParameterAnnotations();
                // inject each parameters with a simple instance or a provided one
                for (int j = 0; j < parameterTypes.length; j++) {
                    arguments[j] = injector.getProviderOrInstance((Class<?>) parameterTypes[j], genericParameterTypes[j],
                            parameterAnnotations[j], constructor);
                }
                boolean accessible = constructor.isAccessible();
                // if the constructor is private, then put it public for newinstance creation
                if (!accessible) {
                    constructor.setAccessible(true);
                }
                // create new instance with the constructor
                try {
                    return c.cast(constructor.newInstance(arguments));
                } finally {
                    // if constructor was private, then put it private back
                    if (!accessible) {
                        constructor.setAccessible(accessible);
                    }
                }
            }
        }
        ExceptionManager
                .makeException("Could not find @Inject constructor for " + c)
                .throwManaged();
        throw new RuntimeException(); // should never append
    }
}
