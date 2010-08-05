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

package cx.ath.mancel01.dependencyshot.spi;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.util.logging.Logger;

/**
 * Manipulates an instance provided by dependecy-shot before it's injected
 * in an object.
 *
 * @author Mathieu ANCELIN
 */
public abstract class InstanceHandler {
    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(InstanceHandler.class.getSimpleName());
    /**
     * @return the validator for this handler.
     */
    public abstract <T extends ImplementationValidator> T getValidator();
    /**
     * @param instance to test.
     * @return if the instance is valid for this handling.
     */
    public abstract boolean isInstanceValid(Object instance);
    /**
     * Manipulate an instance.
     *
     * @param instance the instance.
     * @param interf the instances interface.
     * @param injector th actual injector.
     * @return the handled instance (or proxy or whatever)
     */
    public abstract Object manipulateInstance(Object instance, Class interf, InjectorImpl injector, InjectionPoint point);
    /**
     * Handle an instance.
     * 
     * @param instance the instance.
     * @param interf the instances interface.
     * @param injector th actual injector.
     * @return the handled instance (or proxy or whatever)
     */
    public Object handleInstance(Object instance, Class interf, InjectorImpl injector, InjectionPoint point) {
        if (isInstanceValid(instance)) {
            if (DependencyShot.DEBUG) {
                logger.info(new StringBuilder()
                        .append("Instance '")
                        .append(instance.toString())
                        .append("' handled by ")
                        .append(this.getClass().getSimpleName())
                        .toString());
            }
            return manipulateInstance(instance, interf, injector, point);
        } else {
            if (DependencyShot.DEBUG) {
                logger.info(new StringBuilder()
                        .append("Instance '")
                        .append(instance.toString())
                        .append("' can't be handled by ")
                        .append(this.getClass().getSimpleName())
                        .toString());
            }
            return instance;
        }
    }
}
