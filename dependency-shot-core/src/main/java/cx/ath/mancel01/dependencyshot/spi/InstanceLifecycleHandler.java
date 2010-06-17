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
 *
 * @author Mathieu ANCELIN
 */
public abstract class InstanceLifecycleHandler {

    public abstract <T extends ImplementationValidator> T getValidator();

    public abstract boolean isInstanceValid(Object instance);

    public abstract void postConstruct(Object o);

    public abstract void preDestroy(Object o);

    public abstract Collection<Object> getManagedInstances();

    public void handlePostConstruct(Object instance) {
        if (isInstanceValid(instance)) {
            postConstruct(instance);
        }
    }

    public void handlePreDestroy(Object instance) {
        if (isInstanceValid(instance)) {
            preDestroy(instance);
        }
    }
}
