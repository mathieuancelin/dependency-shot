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

package cx.ath.mancel01.dependencyshot.graph.builder;

import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.graph.BindingBuilder;
import cx.ath.mancel01.dependencyshot.injection.util.InstanceProvider;
import java.lang.annotation.Annotation;
import javax.inject.Provider;

/**
 *
 * @author Mathieu ANCELIN
 */
public class TypesafeBuilder<T> implements FluentBuilder<T>,
        QualifiedBuilder<T>, StagingBuilder<T> {

    private BindingBuilder builder;

    public TypesafeBuilder(BindingBuilder builder) {
        this.builder = builder;
    }

    @Override
    public QualifiedBuilder<T> annotatedWith(Class<? extends Annotation> annotation) {
        builder.setAnnotation(annotation);
        return this;
    }

    @Override
    public QualifiedBuilder<T> named(String named) {
        builder.setNamed(named);
        return this;
    }

    @Override
    public StagingBuilder<T> to(Class<? extends T> to) {
        builder.setTo(to);
        return this;
    }

    @Override
    public StagingBuilder<T> toInstance(Object instance) {
        builder.setProvider(new InstanceProvider(instance));
        return this;
    }

    @Override
    public StagingBuilder<T> providedBy(Provider<? extends T> provider) {
        builder.setProvider(provider);
        return this;
    }

    @Override
    public Builder<Binding> onStage(Stage stage) {
        builder.setStage(stage);
        return builder;
    }

    @Override
    public Binding build() {
        return builder.build();
    }
}
