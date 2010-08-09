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

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.ImplementationValidator;
import cx.ath.mancel01.dependencyshot.spi.InstanceHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.ManagedBean;
import javax.naming.Context;
import javax.naming.InitialContext;

/**
 * Handler for managedbean management.
 *
 * @author Mathieu ANCELIN
 */
public final class ManagedBeanHandler extends InstanceHandler {

    private static final Logger logger = Logger.getLogger(ManagedBeanHandler.class.getSimpleName());

    private boolean registered = false;

    public static boolean isManagedBean(Object o) {
        if (o != null) {
            return isManagedBean(o.getClass());
        }
        return false;
    }

    public static boolean isManagedBean(Class clazz) {
        return clazz.isAnnotationPresent(ManagedBean.class);
    }

    @Override
    public boolean isInstanceValid(Object instance) {
        return new LifecycleValidator().isValid(instance);
    }

    @Override
    public <T extends ImplementationValidator> T getValidator() {
        return (T) new LifecycleValidator();
    }

    @Override
    public Object manipulateInstance(Object instance, Class interf, InjectorImpl injector, InjectionPoint point) {
        if (!registered) { // TODO :  replace this with the upcoming event spi api.
            injector.registerShutdownHook();
            registered = true;
        }
        registerManagedBeanJNDI(instance);
        return instance;
    }

    private void registerManagedBeanJNDI(Object instance) {
        if (System.getProperty(Context.INITIAL_CONTEXT_FACTORY) != null) {
            try {
                Class clazz = instance.getClass();
                if (clazz.isAnnotationPresent(ManagedBean.class)) {
                    ManagedBean annotation = (ManagedBean) clazz.getAnnotation(ManagedBean.class);
                    String name = clazz.getName();
                    if (!annotation.value().equals("")) {
                        name = annotation.value();
                    }
                    name += "_" + instance.hashCode(); // Remove that
                    Context context = new InitialContext();
                    if (context.lookup(name) != null) {
                        context.bind(name, instance);
                    }
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
    }
}
