/*
 *  Copyright 2009-2010 Mathieu ANCELIN
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
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.injection.fluent.Binded;
import cx.ath.mancel01.dependencyshot.injection.fluent.BindedFrom;
import cx.ath.mancel01.dependencyshot.injection.fluent.BindedTo;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
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
     * Context for named injections.
     */
	private Map<Binding<?>, Binding<?>> bindings = new HashMap<Binding<?>, Binding<?>>();

    private DSInjector binderInjector;

	public Binder() { }

    /**
     * Abstract method to configure the binder with bindings.
     */
    @Override
    public abstract void configureBindings();

    /**
     * Binding method
     * 
     * @param <T> type
     * @param from Binded class
     * @param to Implementation of extends of from
     */
	public <T> void bind(Class<T> from, Class<? extends T> to) {
		addBinding(new Binding<T>(from, to));
	}

    /**
     * Binding method
     *
     * @param <T> type
     * @param c Binded class
     */
	public <T> void bind(Class<T> c) {
		addBinding(new Binding<T>(c, c));
	}

    /**
     * Binding method
     *
     * @param <T> type
     * @param qualifier
     * @param from Binded class
     * @param to Implementation of extends of from
     */
	public <T> void bind(Class<? extends Annotation> qualifier, Class<T> from, Class<? extends T> to) {
		addBinding(new Binding<T>(qualifier, from, to));
	}

    /**
     * Binding method
     * 
     * @param <T> type
     * @param name name of the binding @Named
     * @param from Binded class
     * @param provider provide object
     */
	public <T> void bind(String name, Class<T> from, Provider<T> provider) {
		addBinding(new Binding<T>(name, from, provider));
	}

    /**
     * Add a binding to current bindings.
     *
     * @param <T> type
     * @param binding a binding to add.
     */
	private <T> void addBinding(Binding<T> binding) {
        if(bindings.containsKey(binding))
            return;
		Binding<?> old = bindings.put(binding, binding);
		if (old != null) {
			throw new IllegalArgumentException(binding + " overwrites " + old);
		}
	}

    /**
     * Fluent API
     */

//    public <T> BindedTo bind(Class<T> c) { // BindedTo et BindedFrom ?
//        if (c.isAssignableFrom(Annotation.class)) {
//
//        }
//        return null;
//    }
//
//    public <T> BindedFrom bind(String name) {
//        return null;
//    }
//
//    public <T> Binded to(Class<? extends T> to) {
//        return null;
//    }
//
//    public <T> BindedTo from(Class<T> from) { // BindedTo ou BindProvidedBy ?
//        return null;
//    }
//
//    public <T> Binded providedBy(Provider<T> provider) {
//        return null;
//    }
//
    public Map<Binding<?>, Binding<?>> getBindings() {
        return bindings;
    }

    @Override
    public boolean isEmpty() {
        return bindings.isEmpty();
    }

    @Override
    public void setInjector(DSInjector injector) {
        binderInjector = injector;
    }

    public DSInjector getBinderInjector() {
        return binderInjector;
    }
}
