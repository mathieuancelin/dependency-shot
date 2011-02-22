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

package cx.ath.mancel01.dependencyshot.lifecycle;

import cx.ath.mancel01.dependencyshot.spi.ImplementationValidator;
import cx.ath.mancel01.dependencyshot.spi.InstanceLifecycleHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *  Utility class that handle lifecycle management.
 *
 * @author Mathieu ANCELIN
 */
public final class LifecycleHandler  extends InstanceLifecycleHandler {

    private static final Logger logger = Logger.getLogger(LifecycleHandler.class.getSimpleName());

    private Collection<Object> managedInstances = new ArrayList<Object>();

    @Override
    public boolean isInstanceValid(Object instance) {
        return new LifecycleValidator().isValid(instance);
    }

    @Override
    public <T extends ImplementationValidator> T getValidator() {
        return (T) new LifecycleValidator();
    }

    @Override
    public void postConstruct(Object o) {
        invokePostConstruct(o);
    }

    @Override
    public void preDestroy(Object o) {
        invokePreDestroy(o);
    }

    @Override
    public Collection<Object> getManagedInstances() {
        return managedInstances;
    }

    @Override
    public void cleanupAll() {
        for (Object instance : managedInstances) {
            if (instance != null) {
                invokePreDestroy(instance);
            }
        }
        managedInstances.clear();
    }

    @Override
    public void cleanupSome(Collection<?> instances) {
        for (Object instance : instances) {
            if (managedInstances.contains(instance)) {
                if (instance != null) {
                    invokePreDestroy(instance);
                    managedInstances.remove(instance);
                }
            }
        }
    }

    /**
     * Method that handle PostConstrut annotated methods.
     *
     * @param o manipulated object.
     */
    private void invokePostConstruct(Object o) {
        managedInstances.add(o);
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
                    logger.log(Level.SEVERE, null, ex);
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
    private void invokePreDestroy(Object o) {
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
                    logger.log(Level.SEVERE, null, ex);
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
