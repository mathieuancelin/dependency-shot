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

package cx.ath.mancel01.dependencyshot.spi;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu ANCELIN
 */
public abstract class InstanceHandler {

    private static Logger logger = Logger.getLogger(InstanceHandler.class.getName());

    public abstract <T extends ImplementationValidator> T getValidator();

    public abstract boolean isInstanceValid(Object instance);

    public abstract Object manipulateInstance(Object instance, Class interf, InjectorImpl injector);

    public Object handleInstance(Object instance, Class interf, InjectorImpl injector) {
        if (isInstanceValid(instance)) {
            if (DependencyShot.DEBUG) {
                logger.info("Instance '" + instance.toString() + "' handled by " + this.getClass().getSimpleName());
            }
            return manipulateInstance(instance, interf, injector);
        } else {
            if (DependencyShot.DEBUG) {
                logger.info("Instance '" + instance.toString() + "' can't be handled by " + this.getClass().getSimpleName());
            }
            return instance;
        }
    }
}
