/*
 *  Copyright 2009 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependencyshot.api;

/**
 * Injector interface.
 * 
 * @author Mathieu ANCELIN
 */
public interface DSInjector {
    /**
     * @param <T> type of the object.
     * @param type the class to implements.
     * @return the instance.
     */
    <T> T getObjectInstance(Class<T> type);
    /**
     * Injection on fields.
     * @param obj injectable object.
     * @return injected object.
     */
    Object injectFields(Object obj);
    /**
     * injection on an instance.
     * @param obj injectable object.
     * @return injected object.
     */
    Object injectInstance(Object obj);
    /**
     *
     * @param obj injectable object.
     * @return injected object.
     */
    Object injectMembers(Object obj);

}
