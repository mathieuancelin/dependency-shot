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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Handler for managedbean management.
 *
 * @author Mathieu ANCELIN
 */
public final class ManagedBeanHandler extends InstanceHandler {

    private static final Logger logger = Logger.getLogger(ManagedBeanHandler.class.getSimpleName());

    private static List<InjectorImpl> injectors = new ArrayList<InjectorImpl>();

    public static boolean isManagedBean(Object o) {
        if (o != null) {
            return isManagedBean(o.getClass());
        }
        return false;
    }

    public static boolean isManagedBean(Class clazz) {
        return true;
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
        if (!injectors.contains(injector)) { // TODO :  replace this with the upcoming event spi api.
            injectors.add(injector);
            injector.registerShutdownHook();
        }
        return instance;
    }
}
