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
import cx.ath.mancel01.dependencyshot.graph.builder.FluentBuilder;
import cx.ath.mancel01.dependencyshot.graph.builder.TypesafeBuilder;
import java.lang.annotation.Annotation;
import javax.inject.Provider;

/**
 * Utility class to easily create binding.
 * Really usefull for ConfigurationHandler modules.
 *
 * @author Mathieu ANCELIN
 */
public class BindingBuilder implements Builder<Binding>{

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
    public static BindingBuilder prepareBindingThat() {
        return new BindingBuilder();
    }

    /**
     * {@inheritDoc }
     */
    //@Override
    public final <T> FluentBuilder<T> bind(Class<T> from) {
        this.from = from;
        this.to = from;
        this.named = null;
        this.annotation = null;
        this.provider = null;
        this.stage = null;
        return new TypesafeBuilder<T>(this);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public Binding build() {
        return new Binding(this.annotation, this.named,
                        this.from, this.to, this.provider, this.stage);
    }

    public void setAnnotation(Class<? extends Annotation> annotation) {
        this.annotation = annotation;
    }

    public void setNamed(String named) {
        this.named = named;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setTo(Class to) {
        this.to = to;
    }
}
