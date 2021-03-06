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

import cx.ath.mancel01.dependencyshot.api.DslConstants;
import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.api.Nullable;
import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import cx.ath.mancel01.dependencyshot.exceptions.ExceptionManager;
import cx.ath.mancel01.dependencyshot.exceptions.NullInjectionException;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.injection.util.EnhancedProvider;
import cx.ath.mancel01.dependencyshot.injection.util.InstanceProvider;
import cx.ath.mancel01.dependencyshot.scope.ScopeInvocationHandler;
import cx.ath.mancel01.dependencyshot.spi.CustomScopeHandler;
import cx.ath.mancel01.dependencyshot.spi.InstanceHandler;
import cx.ath.mancel01.dependencyshot.util.ReflectionUtil;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Qualifier;

/**
 * Object representation of a binding.
 * 
 * @author Mathieu ANCELIN
 */
public class Binding<T> {

    /**
     * hash value.
     */
    private static final int HASH = 7;
    /**
     * hash key value.
     */
    private static final int HASH_KEY = 79;
    /**
     * Binded class.
     */
    private Class<T> from;
    /**
     * Implementation of extends of @Code from
     */
    private Class<? extends T> to;
    /**
     * Custom qualifier annotation for a binding. 
     * Useful for specific injection.
     */
    private Class<? extends Annotation> qualifier;
    /**
     * Instance provider for an injection (factory like)
     */
    private Provider<T> provider;
    /**
     * Name of a named binding i.e with @Named("something")
     */
    private String name;
    /**
     * Stage of the binding.
     */
    private Stage stage;

    private ScopeInvocationHandler scopeHandler;

    /**
     * New public empty constructor to help the job for
     * spi configurator.
     */
    public Binding() {}
    /**
     * Constructor
     * 
     * @param qualifier qualifier of the binding
     * @param name name of the binding @Named
     * @param from basic binded class
     * @param to implementation or extends of the binded class
     * @param provider provider object
     */
    public Binding(
            Class<? extends Annotation> qualifier,
            String name,
            Class<T> from,
            Class<? extends T> to,
            Provider<T> provider,
            Stage stage) {
        if (qualifier != null && !qualifier.isAnnotationPresent(Qualifier.class)) {
            throw new IllegalArgumentException();
        }
        this.from = from;
        this.qualifier = qualifier;
        this.to = to;
        this.name = name;
        this.provider = provider;
        this.stage = stage;
    }

    /**
     * Constructor for groovy configurator.
     *
     * @param params of the bindings.
     */
    public Binding(Map params) {
        Class qualif = null;
        if (params.containsKey(DslConstants.ANNOTATED_WITH)) {
            qualif = (Class<? extends Annotation>) params.get(DslConstants.ANNOTATED_WITH);
        }
        if (qualif != null && !qualif.isAnnotationPresent(Qualifier.class)) {
            throw new IllegalArgumentException();
        } else {
            this.qualifier = qualif;
        }
        if (params.containsKey(DslConstants.FROM)) {
            this.from = (Class<T>) params.get(DslConstants.FROM);
            this.to = (Class<T>) params.get(DslConstants.FROM);
        } else {
            throw ExceptionManager
                    .makeException(DSIllegalStateException.class, "A binding must contains a 'from' class.")
                    .get();
        }
        if (params.containsKey(DslConstants.TO)) {
            this.to = (Class<? extends T>) params.get(DslConstants.TO);
        }
        if (params.containsKey(DslConstants.NAMED)) {
            this.name = (String) params.get(DslConstants.NAMED);
        }
        if (params.containsKey(DslConstants.PROVIDED_BY)) {
            this.provider = (Provider<T>) params.get(DslConstants.PROVIDED_BY);
        }
        if (params.containsKey(DslConstants.ON_STAGE)) {
            this.stage = (Stage) params.get(DslConstants.ON_STAGE);
        }
        if (params.containsKey(DslConstants.TO_INSTANCE)) {
            this.provider = new InstanceProvider(params.get(DslConstants.TO_INSTANCE));
        }
    }

    /**
     * Constructor for groovy configurator.
     *
     * @param from the binded class of a binding.
     * @param params of the bindings.
     */
    public Binding(Class<T> from, Map params) {
        Class qualif = null;
        if (params.containsKey(DslConstants.ANNOTATED_WITH)) {
            qualif = (Class<? extends Annotation>) params.get(DslConstants.ANNOTATED_WITH);
        }
        if (qualif != null && !qualif.isAnnotationPresent(Qualifier.class)) {
            throw new IllegalArgumentException();
        } else {
            this.qualifier = qualif;
        }
        this.from = from;
        this.to = from;
        if (params.containsKey(DslConstants.TO)) {
            this.to = (Class<? extends T>) params.get(DslConstants.TO);
        }
        if (params.containsKey(DslConstants.NAMED)) {
            this.name = (String) params.get(DslConstants.NAMED);
        }
        if (params.containsKey(DslConstants.PROVIDED_BY)) {
            this.provider = (Provider<T>) params.get(DslConstants.PROVIDED_BY);
        }
        if (params.containsKey(DslConstants.ON_STAGE)) {
            this.stage = (Stage) params.get(DslConstants.ON_STAGE);
        }
        if (params.containsKey(DslConstants.TO_INSTANCE)) {
            this.provider = new InstanceProvider(params.get(DslConstants.TO_INSTANCE));
        }
    }

    /**
     * @return the class of the binding.
     */
    public final Class<T> getFrom() {
        return from;
    }

    /**
     * @return the qualifier of the binding.
     */
    public final Class<? extends Annotation> getQualifier() {
        return qualifier;
    }

    /**
     * @return the target of the binding.
     */
    public final Class<? extends T> getTo() {
        return to;
    }

    /**
     * @return the stage of the binding.
     */
    public final Stage getStage() {
        return stage;
    }

    /**
     * @param to new value for the target class.
     */
    public final void setTo(Class<? extends T> to) {
        this.to = to;
    }

    public Provider<T> getProvider() {
        return provider;
    }

    public final T getInstance(InjectorImpl injector, InjectionPoint point) {
        return getInstance(injector, point, true);
    }

    /**
     * Get an instance of a binded object.
     *
     * @param injector the concerned injector
     * @return binded object
     */
    public final T getInstance(InjectorImpl injector, InjectionPoint point, boolean scoped) {
        // TODO : extension point : inject dynamic
        T result = null;
        Class<? extends Annotation> scope = ReflectionUtil.getScope(to);
        if (provider != null) {
            provider = injector.injectInstance(provider);
            //if (isImplementingEnhancedProvider(provider.getClass().getGenericInterfaces())) {
            if (isImplementingEnhancedProvider(provider.getClass())) {
                result = (T) ((EnhancedProvider) provider).enhancedGet(point);
            } else {
                result = provider.get();
            }
        } else if (scope != null && scoped) {
            CustomScopeHandler scopedInstanceStore = injector.getScopeHandler(scope);
            if (scopedInstanceStore.isBeanValid(from, to)) {
                if (scopedInstanceStore.isDynamic()) {
                    if (scopeHandler == null)
                        scopeHandler =
                            new ScopeInvocationHandler(injector.getScopeHandler(scope),
                                from, to, point, injector);
                    result = (T) ReflectionUtil.getProxyFor(scopeHandler, from);
                } else {
                    result = (T) injector.getScopeHandler(scope).getScopedInstance(from, to, point, injector);
                }
            } else {
                throw ExceptionManager
                        .makeException(DSIllegalStateException.class,
                            "The scope " + scopedInstanceStore.getClass().getSimpleName()
                            + " is invalid. Can't perform injection.")
                        .get();
            }
        } else {
            result = (T) injector.createInstance(to);
        }
        //injector.getInstanciatedClasses().clear();
        boolean nullable = false;
        if (result == null) {
            if (point != null) {
                for(Annotation a : point.getAnnotations()) {
                    if (a.annotationType().isAssignableFrom(Nullable.class)) {
                        nullable = true;
                    }
                }
            }
            if (!nullable) {
                throw ExceptionManager
                        .makeException(NullInjectionException.class,
                            "Could not get a "
                            + to
                            + ". Can't inject object with null value. For that use @Nullable annotation.")
                        .get();
            }
        }
        if (!nullable) {
//            for (InstanceLifecycleHandler handler : injector.getLoader().getLifecycleHandlers()) {
//                handler.handlePostConstruct(result);
//            }
            for (InstanceHandler handler : injector.getLoader().getInstanceHandlers()) {
                result = (T) handler.handleInstance(result, from, injector, point);
            }
//            if (EventListener.class.isAssignableFrom(result.getClass())) {
//                injector.registerEventListener((EventListener) result);
//            }
        }
        return result;
    }

    private boolean isImplementingEnhancedProvider(Class<?> clazz) {
        return EnhancedProvider.class.isAssignableFrom(clazz);
    }

    /**
     * Is this binding having an enhanced provider.
     * 
     * @param interfaces the class to check.
     * @return Is this binding having an enhanced provider.
     */
    private boolean isImplementingEnhancedProvider(Type[] interfaces) {
        boolean ret = false;
        for (Type t : interfaces) {
            if (t.equals(EnhancedProvider.class)) {
                ret = true;
            }
        }
        return ret;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public final boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        Binding<?> other = (Binding<?>) obj;
//        if (from == null) {
//            if (other.from != null) {
//                return false;
//            }
//        } else if (!from.equals(other.from)) {
//            return false;
//        }
//        if (name == null) {
//            if (other.name != null) {
//                return false;
//            }
//        } else if (!name.equals(other.name)) {
//            return false;
//        }
//        if (qualifier == null) {
//            if (other.qualifier != null) {
//                return false;
//            }
//        } else if (!qualifier.equals(other.qualifier)) {
//            return false;
//        }
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Binding<?> other = (Binding<?>) obj;
        if (from == null) {
            if (other.from != null) {
                return false;
            }
        } else if (!from.getName().equals(other.from.getName())) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        if (qualifier == null) {
            if (other.qualifier != null) {
                return false;
            }
        } else if (!qualifier.getName().equals(other.qualifier.getName())) {
            return false;
        }
        return true;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public final int hashCode() {
        int hash = HASH;
//        hash = HASH_KEY * hash + (this.from != null ? this.from.hashCode() : 0);
//        hash = HASH_KEY * hash + (this.qualifier != null ? this.qualifier.hashCode() : 0);
//        hash = HASH_KEY * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = HASH_KEY * hash + (this.from != null ? this.from.getName().hashCode() : 0);
        hash = HASH_KEY * hash + (this.qualifier != null ? this.qualifier.getName().hashCode() : 0);
        hash = HASH_KEY * hash + (this.name != null ? this.name.hashCode() : 0);
        return hash;
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public final String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName());
        builder.append(" [");
        if (from != null) {
            builder.append("from=");
            builder.append(from.getName());
        }
        if (to != null) {
            builder.append(", ");
            builder.append("to=");
            builder.append(to.getName());
        }
        if (qualifier != null) {
            builder.append(", ");
            builder.append("qualifier=");
            builder.append(qualifier.getName());
        }
        if (provider != null) {
            builder.append(", ");
            builder.append("provider=");
            builder.append(provider);
        }
        if (name != null) {
            builder.append(", ");
            builder.append("name=");
            builder.append(name);
        }
        builder.append("]");
        return builder.toString();
    }

    /**
     * Create a fake binding to search it in a map.
     *
     * @param <T> type of the class
     * @param c class to bind
     * @param annotation qualifier
     * @return a fake binding
     */
    public static <T> Binding<T> lookup(Class<T> c, Annotation annotation) {
        if (annotation instanceof Named) {
            Named named = (Named) annotation;
            return new Binding<T>(null, named.value(), c, c, null, null);
        } else if (annotation != null) {
            return new Binding<T>(annotation.annotationType(), null, c, null, null, null);
        } else {
            return new Binding<T>(null, null, c, null, null, null);
        }
    }
}
