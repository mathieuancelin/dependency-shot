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

package cx.ath.mancel01.dependencyshot.graph;

import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.injection.fluent.FluentBinder;
import cx.ath.mancel01.dependencyshot.injection.fluent.QualifiedBinding;
import cx.ath.mancel01.dependencyshot.injection.fluent.StagingBinding;
import java.lang.annotation.Annotation;
import javax.inject.Provider;

/**
 *
 * @author Mathieu ANCELIN
 */
public class TypesafeBinder<T> implements FluentBinder<T>,
        QualifiedBinding<T>, StagingBinding<T> {


    private Binder binder;

    public TypesafeBinder(Binder binder) {
        this.binder = binder;
    }

    @Override
    public QualifiedBinding<T> annotatedWith(Class<? extends Annotation> annotation) {
        binder.annotatedWith(annotation);
        return this;
    }

    @Override
    public QualifiedBinding<T> named(String named) {
        binder.named(named);
        return this;
    }

    @Override
    public StagingBinding<T> to(Class<? extends T> to) {
        binder.to(to);
        return this;
    }

    @Override
    public StagingBinding<T> toInstance(Object instance) {
        binder.toInstance(instance);
        return this;
    }

    @Override
    public StagingBinding<T> providedBy(Provider<? extends T> provider) {
        binder.providedBy(provider);
        return this;
    }

    @Override
    public void onStage(Stage stage) {
        binder.onStage(stage);
    }
}
