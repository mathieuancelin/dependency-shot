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
package cx.ath.mancel01.dependencyshot.graph;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.DSBinding;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import javax.inject.Provider;

/**
 * A binder is a configuration class containing bindings
 * between interface classes and their implementations.
 * At least one binder with one binding is necessary to
 * inject dependencies in your code.
 * To use a binder, define your own binder class extending this one
 * and define your own configureBindings method with your personnal
 * bindings.
 * 
 * @author Mathieu ANCELIN
 */
public abstract class Binder implements DSBinder {

    /**
     * Map of managed bindings.
     */
    private HashMap<Class, DSBinding> bindings;
    /**
     * The waiting binding in case of natural methods use.
     */
    private Binding waitingBinding;
    /**
     * The flag for natural method use.
     */
    private boolean genericWaiting = false;

    /**
     * Constructor.
     */
    public Binder() {
        bindings = new HashMap<Class, DSBinding>();
    }

    /**
     * Abstract method to configure the binder with bindings.
     */
    @Override
    public abstract void configureBindings();

    /**
     * Natural method to bind an interface to one of its implementation.
     * @param generic the generic interface.
     * @return the current binder object to call the to() method.
     */
    @Override
    public final DSBinder bind(final Class generic) {
        waitingBinding = new Binding();
        waitingBinding.setGeneric(generic);
        // theses two lines inject the implementation of the current class.
        waitingBinding.setSpecific(generic);
        this.bindings.put(waitingBinding.getGeneric(), waitingBinding);
        genericWaiting = true;
        return this;
    }

    /**
     * 
     * @param name
     * @return
     */
    @Override
    public final DSBinder bind(final String name) {
        return this;
    }

    /**
     *
     * @param generic
     * @return
     */
    @Override
    public final DSBinder from(final Class generic) {
        return this;
    }

    /**
     * Bind an interface to one of its implementation.
     * @param specific the specific implementation.
     */
    @Override
    public final DSBinder to(final Class specific) {
        if (genericWaiting) {
            waitingBinding.setSpecific(specific);
            this.bindings.put(waitingBinding.getGeneric(), waitingBinding);
            genericWaiting = false;
        }
        return this;
    }

    /**
     *
     * @param qualifier
     * @return
     */
    @Override
    public final DSBinder qualifiedBy(final Class<? extends Annotation>  qualifier) {
        return this;
    }

    /**
     *
     * @param <T>
     * @param provider
     * @return
     */
    @Override
    public final <T> DSBinder providedBy(final Provider<T> provider) {
        return this;
    }

    /**
     *
     * @param name
     * @return
     */
    @Override
    public final DSBinder namedWith(final String name) {
        return this;
    }

    /**
     * Getter on the bindings.
     * @return the current bindings.
     */
    @Override
    public HashMap<Class, DSBinding> getBindings() {
        return bindings;
    }

    /**
     * Setter on the bindings.
     * @param bindings the current bindings value.
     */
    public void setBindings(final HashMap<Class, DSBinding> bindings) {
        this.bindings = bindings;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Binder ");
        builder.append(this.getClass().getSimpleName());
        builder.append(" [");
        builder.append("\n\n");
        for (DSBinding binding : bindings.values()) {
            builder.append("   ");
            builder.append(binding.toString());
            builder.append("\n");
        }
        builder.append("\n]");
        return builder.toString();
    }
}
