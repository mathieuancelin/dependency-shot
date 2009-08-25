/*
 *  Copyright 2009 mathieuancelin.
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
import java.lang.reflect.Method;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieuancelin
 */
public class Binding implements DSBinding {

    public enum SCOPE {

        SINGLETON,
        NORMAL
    };
    private Class generic;
    private Class specific;
    private Vector<Object> specificInstances;
    private Object uniqueInstance;
    private SCOPE scope;
    private Vector<DSInterceptor> managedInterceptors = new Vector();

    public Class getGeneric() {
        return generic;
    }

    public void setGeneric(Class generic) {
        this.generic = generic;
    }

    public SCOPE getScope() {
        return scope;
    }

    public void setScope(SCOPE scope) {
        this.scope = scope;
    }

    public Class getSpecific() {
        return specific;
    }

    public void setSpecific(Class specific) {
        this.specific = specific;
    }

    public Vector<Object> getSpecificInstances() {
        return specificInstances;
    }

    public void setSpecificInstances(Vector<Object> specificInstances) {
        this.specificInstances = specificInstances;
    }

    public void addSpecificInstance(Object obj) {
        this.specificInstances.add(obj);
    }

    public void remSpecificInstance(Object obj) {
        this.specificInstances.remove(obj);
    }

    public Object getUniqueInstance() {
        return uniqueInstance;
    }

    public void setUniqueInstance(Object uniqueInstance) {
        this.uniqueInstance = uniqueInstance;
    }

    @Override
    public Object getSpecificInstance() {
        try {
            Object o = this.specific.newInstance();
            return processInterceptorsAnnotations(o, this.getGeneric());// check if injectable ?
        } catch (Exception ex) {
            Logger.getLogger(Binding.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    private Object processInterceptorsAnnotations(Object obj, Class interfaceClazz) {// ne fonctionne pas avec plusieurs classes intercepteurs
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
        if (managedInterceptors.size() > 0) {
            managedInterceptors.add(new FinalInterceptor());
            DSInterceptor[] interceptors = new DSInterceptor[managedInterceptors.size()];
            int i = 0;
            for (Object o : managedInterceptors) {
                interceptors[i] = (DSInterceptor) o;
                i++;
            }
            ret = Weaver.getInstance().weaveObject(interfaceClazz, obj, interceptors);
        }
        return ret;
    }

    private void findAroundInvoke(Class clazz) {
        Interceptors inter = (Interceptors) clazz.getAnnotation(Interceptors.class);
        Object interceptorInstance = null;
        for (Class c : inter.value()) {
            try {
                interceptorInstance = c.newInstance();
                for (Method m : c.getDeclaredMethods()) {
                    if (m.isAnnotationPresent(AroundInvoke.class)) {
                        managedInterceptors.add(new UserInterceptor(m, interceptorInstance));
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(AnnotationsProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void findAroundInvoke(Method method) {
        Interceptors inter = (Interceptors) method.getAnnotation(Interceptors.class);
        Object interceptorInstance = null;
        for (Class c : inter.value()) {
            try {
                interceptorInstance = c.newInstance();
                for (Method m : c.getDeclaredMethods()) {
                    if (m.isAnnotationPresent(AroundInvoke.class)) {
                        UserInterceptor interceptorTmp = new UserInterceptor(m, interceptorInstance);
                        interceptorTmp.setAnnotedMethod(method);
                        managedInterceptors.add(interceptorTmp);
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(AnnotationsProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
