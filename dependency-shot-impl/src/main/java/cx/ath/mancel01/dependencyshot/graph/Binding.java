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

import cx.ath.mancel01.dependencyshot.aop.Weaver;
import cx.ath.mancel01.dependencyshot.aop.FinalInterceptor;
import cx.ath.mancel01.dependencyshot.aop.UserInterceptor;
import cx.ath.mancel01.dependencyshot.api.DSInterceptor;
import cx.ath.mancel01.dependencyshot.api.DSBinding;
import cx.ath.mancel01.dependencyshot.api.annotations.Interceptors;
import cx.ath.mancel01.dependencyshot.api.annotations.AroundInvoke;
import cx.ath.mancel01.dependencyshot.injection.AnnotationsProcessor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Provider;

/**
 * Object representation of a binding.
 * 
 * @author Mathieu ANCELIN
 */
public class Binding<T> implements DSBinding {
    /**
     * The scope of the binding.
     */
    public enum SCOPE {
        /**
         * Unique instance.
         */
        SINGLETON,
        /**
         * Normal scope.
         */
        NORMAL
    };
    /**
     * The generic class of the binding (interface).
     */
    private Class<T> generic;//private Class generic;
    /**
     * The specific implementation.
     */
    private Class<? extends T> specific;//private Class specific;
    /**
     * The possible qualifier on a binding.
     */
	private Class<? extends Annotation> qualifier;
    /**
     * JSR 330 provider for a binding;
     */
	private Provider<T> provider;
    /**
     * Managed specific instances.
     */
    private Vector<Object> specificInstances = new Vector();
    /**
     * The unique instance (if SINGLETON scope).
     */
    private Object uniqueInstance;
    /**
     * The current scope.
     */
    private SCOPE scope;
    /**
     * The binding name (if named injection).
     */
    private String name;
    /**
     * The managed interceptors.
     */
    private Vector<DSInterceptor> managedInterceptors = new Vector();
    /**
     * Default constructor.
     */
    public Binding() {
    }
    
    /**
     * @return the generic
     */
    @Override
    public Class<T> getGeneric() {
        return generic;
    }

    /**
     * @param generic the generic to set
     */
    public void setGeneric(final Class<T> generic) {
        this.generic = generic;
    }

    /**
     * @return the specific
     */
    public Class<? extends T> getSpecific() {
        return specific;
    }

    /**
     * @param specific the specific to set
     */
    public void setSpecific(final Class<? extends T> specific) {
        this.specific = specific;
    }

    /**
     * @param qualifier the qualifier of the binding.
     */
    public void setQualifier(final Class<? extends Annotation> qualifier) {
        this.qualifier = qualifier;
    }

    /**
     * @param provider the provider of the binding.
     */
    public void setProvider(final Provider<T> provider) {
        this.provider = provider;
    }

    /**
     * @return the qualifier of the binding.
     */
    public Class<? extends Annotation> getQualifier() {
        return qualifier;
    }

    /**
     * @return the provider of the binding.
     */
    public Provider<T> getProvider() {
        return provider;
    }

    /**
     * @return the specificInstances
     */
    public Vector<Object> getSpecificInstances() {
        return specificInstances;
    }

    /**
     * @param specificInstances the specificInstances to set
     */
    public void setSpecificInstances(final Vector<Object> specificInstances) {
        this.specificInstances = specificInstances;
    }

    /**
     * @return the uniqueInstance
     */
    public Object getUniqueInstance() {
        return uniqueInstance;
    }

    /**
     * @param uniqueInstance the uniqueInstance to set
     */
    public void setUniqueInstance(final Object uniqueInstance) {
        this.uniqueInstance = uniqueInstance;
    }

    /**
     * @return the scope
     */
    public SCOPE getScope() {
        return scope;
    }

    /**
     * @param scope the scope to set
     */
    public void setScope(final SCOPE scope) {
        this.scope = scope;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @return the managedInterceptors
     */
    public Vector<DSInterceptor> getManagedInterceptors() {
        return managedInterceptors;
    }

    /**
     * @param managedInterceptors the managedInterceptors to set
     */
    public void setManagedInterceptors(final Vector<DSInterceptor> managedInterceptors) {
        this.managedInterceptors = managedInterceptors;
    }
    /**
     * @return a specific instance of specific class.
     */
    @Override
    public Object getSpecificInstance() {
        try {
            Object o = this.getSpecific().newInstance();
            this.specificInstances.add(o);
            return processInterceptorsAnnotations(o, this.getGeneric()); // check if injectable ?
        } catch (Exception ex) {
            Logger.getLogger(Binding.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    @Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " [");
		if (generic != null) {
            builder.append("from=");
            builder.append(generic.getName());   
		}
		if (specific != null) {
            builder.append(", ");
            builder.append("to=");
            builder.append(specific.getName());
		}
		if (qualifier != null) {
            builder.append(", ");
            builder.append("qualifier=");
            builder.append(qualifier);
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
     * Check if the object is interceptable.
     * If it is, this method add interceptors chain on it.
     *
     * @param obj the concerned object.
     * @param interfaceClazz the interface.
     * @return the object with interceptor handler (if annotations are presents)
     */
    private Object processInterceptorsAnnotations(
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
                Logger.getLogger(AnnotationsProcessor.class.getName())
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
                Logger.getLogger(AnnotationsProcessor.class.getName())
                        .log(Level.SEVERE, null, ex);
            }
        }
    }
}
