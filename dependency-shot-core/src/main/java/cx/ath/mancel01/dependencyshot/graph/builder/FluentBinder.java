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

package cx.ath.mancel01.dependencyshot.graph.builder;

import java.lang.annotation.Annotation;

/**
 * Interface for the binder.
 *
 * @author Mathieu ANCELIN
 */
public interface FluentBinder<T> extends QualifiedBinding<T>, StagingBinding<T> {

    /**
     * Specify a qualfier for a binding.
     *
     * @param <T> type.
     * @param annotation the qualifier of the binding.
     * @return the actual binder.
     */
    QualifiedBinding<T> annotatedWith(Class<? extends Annotation> annotation);
    /**
     * Specify a name qualifier for a binding.
     *
     * @param <T> type.
     * @param named the name for the qualifier.
     * @return the actual binder.
     */
    QualifiedBinding<T> named(String named);

}
