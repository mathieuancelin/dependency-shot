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

package cx.ath.mancel01.dependencyshot.injection.fluent;

import cx.ath.mancel01.dependencyshot.api.Stage;
import java.lang.annotation.Annotation;
import javax.inject.Provider;

/**
 * Interface for the binder.
 *
 * @author Mathieu ANCELIN
 */
public interface FluentBinder {

    /**
     * Specify a qualfier for a binding.
     *
     * @param <T> type.
     * @param annotation the qualifier of the binding.
     * @return the actual binder.
     */
    <T> QualifiedBinding annotatedWith(Class<? extends Annotation> annotation);
    /**
     * Specify a name qualifier for a binding.
     *
     * @param <T> type.
     * @param named the name for the qualifier.
     * @return the actual binder.
     */
    <T> QualifiedBinding named(String named);
    /**
     * Specify a provider for the actual binding.
     *
     * @param <T> type.
     * @param provider the provider for the binding.
     * @return the actual binder.
     */
    <T> StagingBinding providedBy(Provider<T> provider);
    /**
     * The target class for a binding.
     *
     * @param <T> type.
     * @param to the targeted class for the binding.
     * @return the actual binder.
     */
    <T> StagingBinding to(Class<? extends T> to);
    /**
     * Specify a specific instance to bind with.
     *
     * @param <T> type.
     * @param instance specify the instance.
     * @return the actual binder.
     */
    <T> StagingBinding toInstance(Object instance);
    /**
     * Specify the stage of the binding.
     *
     * @param stage the actual stage.
     */
    void onStage(Stage stage);

}
