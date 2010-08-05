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

import cx.ath.mancel01.dependencyshot.graph.builder.Builder;
import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.graph.builder.BindBuilder;
import cx.ath.mancel01.dependencyshot.graph.builder.FluentBuilder;
import cx.ath.mancel01.dependencyshot.graph.builder.QualifiedBuilder;
import cx.ath.mancel01.dependencyshot.graph.builder.StagingBuilder;
import cx.ath.mancel01.dependencyshot.injection.util.InstanceProvider;
import java.lang.annotation.Annotation;
import javax.inject.Provider;

/**
 * Utility class to easily create binding.
 * Really usefull for ConfigurationHandler modules.
 *
 * @author Mathieu ANCELIN
 */
public class BindingBuilder implements BindBuilder,
                                    FluentBuilder,
                                    QualifiedBuilder,
                                    StagingBuilder,
                                    Builder<Binding> {

    private Class from = null;
    private Class to = null;
    private String named = null;
    private Class<? extends Annotation> annotation = null;
    private Provider provider = null;
    private Stage stage = null;

    /**
     * Constructor.
     */
    private BindingBuilder() {          
    }

    /**
     * Instanciate the builder to create bindings.
     * @return
     */
    public static BindBuilder prepareBindingThat() {
        return new BindingBuilder();
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final <T> FluentBuilder bind(Class<T> from) {
        this.from = from;
        this.to = from;
        this.named = null;
        this.annotation = null;
        this.provider = null;
        this.stage = null;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final <T> QualifiedBuilder annotatedWith(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final <T> QualifiedBuilder named(String named) {
        this.named = named;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> StagingBuilder providedBy(Provider<T> provider) {
        this.provider = provider;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> StagingBuilder to(Class<? extends T> to) {
        this.to = to;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public <T> StagingBuilder toInstance(Object instance) {
        this.provider = new InstanceProvider(instance);
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Builder onStage(Stage stage) {
        this.stage = stage;
        return this;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Binding build() {
        return new Binding(this.annotation, this.named,
                        this.from, this.to, this.provider, this.stage);
    }
}
