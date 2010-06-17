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

package cx.ath.mancel01.dependencyshot.api;

/**
 * Interface for a binder.
 * 
 * @author Mathieu ANCELIN
 */
public interface DSBinder {
    /**
     * configure the binder's bindings.
     */
    void configureBindings();
    /**
     * @return if a binder contains no binding.
     */
    boolean isEmpty();
    /**
     * Set the injector of a binder.
     *
     * @param injector the injector of the binder.
     */
    void setInjector(DSInjector injector);
    /**
     * Configuration of the last binding (workaround for fluent chained binding API)
     */
    void configureLastBinding();
}
