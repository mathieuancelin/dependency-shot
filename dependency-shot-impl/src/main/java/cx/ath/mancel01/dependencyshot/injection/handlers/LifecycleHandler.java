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

package cx.ath.mancel01.dependencyshot.injection.handlers;

import cx.ath.mancel01.dependencyshot.api.annotations.PostConstruct;
import cx.ath.mancel01.dependencyshot.api.annotations.PreDestroy;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *  Utility class that handle lifecycle management.
 *
 * @author Mathieu ANCELIN
 */
public class LifecycleHandler {

    private LifecycleHandler() {}
    /**
     * Method that handle PostConstrut annotated methods.
     *
     * @param o manipulated object.
     */
    public static void invokePostConstruct(Object o) {
        Class clazz = o.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PostConstruct.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                boolean accessible = method.isAccessible();
                // set a private method as public method to invoke it
                if (!accessible) {
                    method.setAccessible(true);
                }
                // invocation of the method with rights parameters
                try {
                    method.invoke(o, parameters);
                } catch (Exception ex) {
                    Logger.getLogger(LifecycleHandler.class.getName()).
                            log(Level.SEVERE, null, ex);
                } finally {
                    // if method was private, then put it private back
                    if (!accessible) {
                        method.setAccessible(accessible);
                    }
                }
            }
        }       
    }
    /**
     * Method that handle PreDestroy annotated methods.
     *
     * @param o manipulated object.
     */
    public static void invokePreDestroy(Object o) {
        Class clazz = o.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PreDestroy.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                boolean accessible = method.isAccessible();
                // set a private method as public method to invoke it
                if (!accessible) {
                    method.setAccessible(true);
                }
                // invocation of the method with rights parameters
                try {
                    method.invoke(o, parameters);
                } catch (Exception ex) {
                    Logger.getLogger(LifecycleHandler.class.getName()).
                            log(Level.SEVERE, null, ex);
                } finally {
                    // if method was private, then put it private back
                    if (!accessible) {
                        method.setAccessible(accessible);
                    }
                }
            }
        }
    }
}
