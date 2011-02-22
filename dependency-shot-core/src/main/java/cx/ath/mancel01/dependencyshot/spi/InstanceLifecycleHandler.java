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

import java.util.Collection;

/**
 * Lifecycle phases handler.
 * 
 * @author Mathieu ANCELIN
 */
public abstract class InstanceLifecycleHandler {
    /**
     * @return validator for this hanler.
     */
    public abstract <T extends ImplementationValidator> T getValidator();
    /**
     * @param instance
     * @return if instance is valid for this handler.
     */
    public abstract boolean isInstanceValid(Object instance);
    /**
     * Handle a postconstruct event.
     * @param o handled instance.
     */
    public abstract void postConstruct(Object o);
    /**
     * Handle a predestroy event.
     * @param o handled instance.
     */
    public abstract void preDestroy(Object o);
    /**
     * @return all the managed instances.
     */
    public abstract Collection<Object> getManagedInstances();
    /**
     * Handle a postconstruct event.
     * @param instance handled instance.
     */
    public void handlePostConstruct(Object instance) {
        if (isInstanceValid(instance)) {
            postConstruct(instance);
        }
    }
    /**
     * Handle a predestroy event.
     * @param instance handled instance.
     */
    public void handlePreDestroy(Object instance) {
        if (isInstanceValid(instance)) {
            preDestroy(instance);
        }
    }

    public abstract void cleanupAll();
    public abstract void cleanupSome(Collection<?> instances);
}
