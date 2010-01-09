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

import cx.ath.mancel01.dependencyshot.aop.FinalInterceptor;
import cx.ath.mancel01.dependencyshot.aop.UserInterceptor;
import cx.ath.mancel01.dependencyshot.aop.Weaver;
import cx.ath.mancel01.dependencyshot.api.DSBinding;
import cx.ath.mancel01.dependencyshot.api.DSInterceptor;
import cx.ath.mancel01.dependencyshot.api.annotations.AroundInvoke;
import cx.ath.mancel01.dependencyshot.api.annotations.Interceptors;
import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Qualifier;
import javax.inject.Singleton;

/**
 * Object representation of a binding.
 * 
 * @author Mathieu ANCELIN
 */
public class Binding<T> implements DSBinding {
    
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
     * The managed interceptors.
     */
    private Vector<DSInterceptor> managedInterceptors = new Vector();
    
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
			Provider<T> provider) {
		if (qualifier != null && !qualifier.isAnnotationPresent(Qualifier.class)) {
			throw new IllegalArgumentException();
		}
		this.from = from;
		this.qualifier = qualifier;
		this.to = to;
		this.name = name;
		this.provider = provider;
	}

	Class<T> getFrom() {
		return from;
	}

	Class<? extends Annotation> getQualifier() {
		return qualifier;
	}

	Class<? extends T> getTo() {
		return to;
	}

    public Vector<DSInterceptor> getManagedInterceptors() {
        return managedInterceptors;
    }


    /**
     * Get an instance of a binded object.
     *
     * @param injector the concerned injector
     * @return binded object
     */
	public T getInstance(InjectorImpl injector) { 
		T result = null;
		if (provider != null) {
			result = provider.get();
		} else if (to.isAnnotationPresent(Singleton.class)) {
            //result = injector.getSingleton(to);
			result = (T) scannInterceptorsAnnotations(injector.getSingleton(to), from);
		} else {
			//result = injector.createInstance(to);
            result = (T) scannInterceptorsAnnotations(injector.createInstance(to), from);
		}
		if (result == null) {
			throw new DSIllegalStateException("Could not get a " + to);
		}
        injector.addManagedInstance(result);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Binding<?> other = (Binding<?>) obj;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (qualifier == null) {
			if (other.qualifier != null)
				return false;
		} else if (!qualifier.equals(other.qualifier))
			return false;
		return true;
	}

    @Override
	public int hashCode() {
		final int prime = 89;
		int result = 1;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((qualifier == null) ? 0 : qualifier.hashCode());
		return result;
	}

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " [");
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
            return new Binding<T>(null, named.value(), c, c, null);
		} else if (annotation != null) {
            return new Binding<T>(annotation.annotationType(), null, c, null, null);
		} else {
            return new Binding<T>(null, null, c, null, null);
		}
	}
    
    /**
     * Check if the object is interceptable.
     * If it is, this method add interceptors chain on it.
     *
     * @param obj the concerned object.
     * @param interfaceClazz the interface.
     * @return the object with interceptor handler (if annotations are presents)
     */
    private Object scannInterceptorsAnnotations(
            final Object obj,
            final Class interfaceClazz) {
        Class clazz = obj.getClass();
        Object ret = obj;
        if (interfaceClazz.isAnnotationPresent(Interceptors.class)) {
            findAroundInvoke(interfaceClazz);
        }
        if (clazz.isAnnotationPresent(Interceptors.class)) {
            findAroundInvoke(clazz);
        }
        for (Method m : interfaceClazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Interceptors.class)) {
                findAroundInvoke(m);
            }
        }
        for (Method m : clazz.getDeclaredMethods()) {
            if (m.isAnnotationPresent(Interceptors.class)) {
                findAroundInvoke(m);
            }
        }
        if (getManagedInterceptors().size() > 0) {
            getManagedInterceptors().add(new FinalInterceptor());
            DSInterceptor[] interceptors =
                    new DSInterceptor[getManagedInterceptors().size()];
            int i = 0;
            for (Object o : getManagedInterceptors()) {
                interceptors[i] = (DSInterceptor) o;
                i++;
            }
            ret = Weaver.getInstance()
                    .weaveObject(interfaceClazz, obj, interceptors);
        }
        return ret;
    }
    /**
     * Check for @AroundInvoke on a class.
     * @param clazz the checked class.
     */
    private void findAroundInvoke(final Class clazz) {
        Interceptors inter =
                (Interceptors) clazz.getAnnotation(Interceptors.class);
        Object interceptorInstance = null;
        for (Class c : inter.value()) {
            try {
                interceptorInstance = c.newInstance();
                for (Method m : c.getDeclaredMethods()) {
                    if (m.isAnnotationPresent(AroundInvoke.class)) {
                        getManagedInterceptors()
                                .add(
                                    new UserInterceptor(m, interceptorInstance)
                                 );
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Binding.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
    /**
     * Check for @AroundInvoke on a method.
     * @param method the checked method.
     */
    private void findAroundInvoke(final Method method) {
        Interceptors inter = (Interceptors)
                method.getAnnotation(Interceptors.class);
        Object interceptorInstance = null;
        for (Class c : inter.value()) {
            try {
                interceptorInstance = c.newInstance();
                for (Method m : c.getDeclaredMethods()) {
                    if (m.isAnnotationPresent(AroundInvoke.class)) {
                        UserInterceptor interceptorTmp =
                                new UserInterceptor(m, interceptorInstance);
                        interceptorTmp.setAnnotedMethod(method);
                        getManagedInterceptors().add(interceptorTmp);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(Binding.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
