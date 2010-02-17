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
package cx.ath.mancel01.dependencyshot.injection;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.api.Stages;
import cx.ath.mancel01.dependencyshot.api.annotations.InjectLogger;
import cx.ath.mancel01.dependencyshot.api.annotations.Property;
import cx.ath.mancel01.dependencyshot.exceptions.DSException;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.handlers.ClassHandler;
import cx.ath.mancel01.dependencyshot.injection.handlers.ConstructorHandler;
import cx.ath.mancel01.dependencyshot.injection.handlers.LifecycleHandler;
import cx.ath.mancel01.dependencyshot.injection.util.LoggerProvider;
import cx.ath.mancel01.dependencyshot.injection.util.PropertiesProvider;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;
import javax.inject.Qualifier;

/**
 * This class represent an injector configured by coded
 * binders.
 * 
 * @author Mathieu ANCELIN
 */
public class InjectorImpl implements DSInjector {

    /**
     * Binders linked to the project.
     */
    private List<Binder> binders;
    /**
     *
     */
    private Map<Binding<?>, Binding<?>> bindings = null;
    /**
     * Managed Object instances.
     */
    private List<Object> registeredManagedBeans;
    /**
     * Singleton scoped object singletonContext.
     */
    private Map<Class<?>, Object> singletonContext;

    private Stages stage = null;

    /**
     * The constructor.
     */
    public InjectorImpl() {
        binders = new ArrayList();
        singletonContext = new HashMap<Class<?>, Object>();
        registeredManagedBeans = new ArrayList<Object>();
    }

    public InjectorImpl(Stages stage) {
        binders = new ArrayList();
        singletonContext = new HashMap<Class<?>, Object>();
        registeredManagedBeans = new ArrayList<Object>();
        this.stage = stage;
    }

    /**
     * Configure all present binders of the injector.
     */
    public final void configureBinders() {
        if (binders.size() > 0) {
            for (DSBinder binder : binders) {
                binder.configureBindings();
                binder.configureLastBinding();
                if (binder.isEmpty()) {
                    Logger.getLogger(InjectorImpl.class.getName()).
                            log(Level.SEVERE, "Ooops, no bindings presents, "
                            + "can't inject your apps ...");
                    throw new DSException("No bindings loaded");
                }
            }
        }
    }

    /**
     *
     * @param o
     */
    public final void addManagedBeanInstance(Object o) {
        this.registeredManagedBeans.add(o); //TODO : object pointer only
    }

    /**
     *
     */
    public final void resetManagedBeanInstances() {
        this.registeredManagedBeans.clear();
    }

    /**
     *
     * @param registeredManagedBeans
     */
    public final void setRegisteredManagedBeans(List<Object> registeredManagedBeans) {
        this.registeredManagedBeans = registeredManagedBeans;
    }

    /**
     * 
     * @return
     */
    public final List<Object> getRegisteredManagedBeans() {
        return registeredManagedBeans;
    }

    /**
     * Add a binder in the injector.
     *
     * @param binder the binder to add.
     */
    public final void addBinder(final DSBinder binder) {
        binder.setInjector(this);
        binders.add((Binder) binder);
    }

    /**
     * Get the defined bindings in all binders of the injector.
     * 
     * @return current bindings
     */
    public final Map<Binding<?>, Binding<?>> bindings() { //TODO : replace for real multi-binder and better perf
        if (bindings == null) {
            bindings = new HashMap<Binding<?>, Binding<?>>();
            for (Binder binder : binders) {
                for (Binding<?> binding : binder.getBindings().keySet()) {
                    bindings.put(binding, binder.getBindings().get(binding));
                }
            }

            Binding loggerBinding = new Binding(InjectLogger.class, null, Logger.class,
                    Logger.class, new LoggerProvider(), null);

            Binding propertiesBinding = new Binding(Property.class, null, String.class,
                    String.class, new PropertiesProvider(), null);

            Binding injectorBinding = new Binding(null, null, DSInjector.class,
                    DSInjector.class, new Provider() {

                @Override
                public Object get() {
                    return ClassHandler.getCurrentInjector();
                }
            }, null);
            bindings.put(loggerBinding, loggerBinding);
            bindings.put(propertiesBinding, propertiesBinding);
            bindings.put(injectorBinding, injectorBinding);
        }
        return bindings;
    }

    /**
     * Get an injected instance of c
     *
     * @param <T> type
     * @param c class of the instance
     * @return instance of c
     */
    @Override
    public final <T> T getInstance(Class<T> c) {
        return getInstance(c, null, null);
    }

    /**
     * Get an injected qualified instance of c
     *
     * @param <T> type
     * @param c class of the instance
     * @param qualifier instance qualifier
     * @return instance of c
     */
    private <T> T getInstance(Class<T> c, Annotation qualifier, InjectionPoint point) {
        Binding<T> binding = getBinding(c, qualifier);
        return binding.getInstance(this, point);
    }

    /**
     * Get an existing binding in current bindings.
     *
     * @param <T> type
     * @param c class of the binding
     * @param annotation annotation of the binding
     * @return exsiting binding
     */
    private <T> Binding<T> getBinding(Class<T> c, Annotation annotation) {
        Binding<T> b = (Binding<T>) bindings().get(Binding.lookup(c, annotation));
        if (b != null) {
            return b;
        } else {
            // add binding for single bindings
            this.bindings.put(new Binding<T>(null, null,
                    c, c, null, null), new Binding<T>(null, null,
                    c, c, null, null));
            b = getBinding(c, annotation);
            if (b != null) {
                return b;
            }
            throw new IllegalStateException("No binding for " + c + " and " + annotation);
        }
        //throw new IllegalStateException("No binding for " + c + " and " + annotation);
    }

    /**
     * Get a singleton instance of a class @Singleton
     *
     * @param <T> type
     * @param c class of the new instance
     * @return singleton instance of c
     */
    public final <T> T getSingleton(Class<T> c) {
        // check if the singleton is present in the singleton context
        T result = c.cast(singletonContext.get(c));
        // if not, create one
        if (result == null) {
            result = createInstance(c);
            singletonContext.put(c, result);
        }
        return result;
    }

    /**
     * Create a new instance of a class
     *
     * @param <T> type
     * @param c class of the new instance
     * @return new instance of c
     */
    public final <T> T createInstance(Class<T> c) {
        try {
            // create a new instance of a class
            T result = ConstructorHandler.getConstructedInstance(c, this);
            // and inject it !!
            ClassHandler.classInjection(result, c, new ArrayList<Method>(), false, this);
            return result;
        } catch (Exception e) {
            throw new DSException(e);
        }
    }

    /**
     * Injection on an instance.
     * WARNING : Constructor injection doesn't work
     * with this kind of injection.
     *
     * @param <T> type
     * @param instance of T
     * @return injected instance
     */
    @Override
    public final <T> T injectInstance(T instance) {
        try {
            T result = instance;
            // and inject it !!
            ClassHandler.classInjection(result,
                    instance.getClass(), new ArrayList<Method>(), false, this);
            return result;
        } catch (Exception e) {
            throw new DSException(e);
        }
    }

    /**
     *
     * @param c
     */
    @Override
    public final void injectStaticMembers(Class<?> c) {
        try {
            List<Method> emptyList = Collections.emptyList();
            ClassHandler.classInjection(null, c, emptyList, true, this);
        } catch (Exception e) {
            throw new DSException("Could not inject static members for " + c, e);
        }
    }

    /**
     * Get an object instance of a qualified injection from a normal binding or
     * a defined provider.
     * 
     * @param type class of the object.
     * @param genericType type of the object.
     * @param annotations annotations present on the field or the method
     * @return
     */
    public final Object getProviderOrInstance(Class<?> type, Type genericType, Annotation[] annotations, Member m) {
        Object value;
        Annotation qualifier = null;
        // search in custom annotations wich one is a qualifier
        for (int i = 0; i < annotations.length; i++) {
            Annotation annotation = annotations[i];
            if (annotation.annotationType().isAnnotationPresent(Qualifier.class)) {
                // when found, do the rest
                qualifier = annotation;
                break;
            }
        }
        // if provided, then return an injected provider
        if (Provider.class.isAssignableFrom(type) && genericType instanceof ParameterizedType) {
            ParameterizedType providerType = (ParameterizedType) genericType;
            final Class<?> providedType = (Class<?>) providerType.getActualTypeArguments()[0];
            final Annotation finalQualifier = qualifier;
            final InjectionPoint point = new InjectionPointImpl(providerType,
                    new HashSet(Arrays.asList(annotations)), m, type);
            value = new Provider() {

                @Override
                public Object get() {
                    return getInstance(providedType, finalQualifier, point);
                }
            };
        } else { // or get a simple instance
            value = getInstance(type, qualifier, new InjectionPointImpl(genericType,
                    new HashSet(Arrays.asList(annotations)), m, type));
        }
        return value;
    }

    /**
     * Reset all the binders of an injector.
     */
    public final void resetBinders() {
        this.binders = new ArrayList();
        this.singletonContext = new HashMap<Class<?>, Object>();
    }

    /**
     *
     * @throws Throwable
     */
    @Override
    protected final void finalize() throws Throwable {
        for (Object o : registeredManagedBeans) {
            LifecycleHandler.invokePreDestroy(o);
            o = null;
        }
        registeredManagedBeans.clear();
        super.finalize();
    }

    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        for (Binding b : bindings.values()) {
            builder.append(b);
            builder.append("\n");
        }
        return builder.toString();
    }

    @Override
    public Stages getStage() {
        return stage;
    }
}
