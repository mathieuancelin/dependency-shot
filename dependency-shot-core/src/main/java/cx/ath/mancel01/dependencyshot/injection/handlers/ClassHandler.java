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
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Utility class that handle injection on Classes.
 *
 * @author Mathieu ANCELIN
 */
public final class ClassHandler {

    private static final Logger logger = Logger.getLogger(ClassHandler.class.getSimpleName());

    private FieldsHandler fieldsHandler;
    
    private MethodHandler methodHandler;

    /**
     * Constructor.
     */
    public ClassHandler() {
        fieldsHandler = new FieldsHandler();
        methodHandler = new MethodHandler();
    }

    /**
     * Perform a complete injection on an object instance.
     *
     * @param <T> type of the object instance.
     * @param instance instance of the object. 
     * @param c class of the object
     * @param maybeOverrides possible overridden methods
     * @param staticInjection can we inject static stuff
     * @param injector the concerned injector
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    public <T> void classInjection(
            T instance,
            Class<?> c,
            List<Method> maybeOverrides,
            boolean staticInjection,
            InjectorImpl injector) throws IllegalAccessException,
            InvocationTargetException {
        Class<?> superclass = c.getSuperclass();
        // check if c has a superclass and if we can inject static stuff
        if (superclass != null && !staticInjection) {
            // get possible overridden method
            List<Method> overideMethodOfTheClass = new ArrayList<Method>(maybeOverrides);
            // and add methods of the class
            overideMethodOfTheClass.addAll(Arrays.asList(c.getDeclaredMethods()));
            // inject the superclass stuff in the instance
            classInjection(instance, superclass, overideMethodOfTheClass, staticInjection, injector);
        }
        // inject fields of the instance
        fieldsHandler.fieldsInjection(instance, c, staticInjection, injector);
        // inject methods of the instance
        methodHandler.methodsInjection(instance, c, maybeOverrides, staticInjection, injector);
    }
}
