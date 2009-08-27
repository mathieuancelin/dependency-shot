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
package cx.ath.mancel01.dependencyshot.injection.handlers;

import cx.ath.mancel01.dependencyshot.api.DSAnnotationHandler;
import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.DSBinding;
import cx.ath.mancel01.dependencyshot.graph.GraphHelper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles inject annotation.
 * 
 * @author Mathieu ANCELIN
 */
public final class InjectHandler implements DSAnnotationHandler {
    /**
     * The unique InjectHandler of the class.
     */
    private static InjectHandler instance = null;

    /**
     * The private constructor of the singleton.
     */
    private InjectHandler() {
    }
    /**
     * The accessor for the unique InjectHandler of the singleton.
     * @return the unique InjectHandler of the singleton.
     */
    public static synchronized InjectHandler getInstance() {
        if (instance == null) {
            instance = new InjectHandler();
        }
        return instance;
    }
    /**
     * Injection on constructor.
     * @param constructor constructor to inject.
     * @param binders concerned binders.
     * @return the injected object.
     */
    public Object injectConstructor(final Constructor constructor, final Vector<DSBinder> binders) {
        Object ret = null;
        Constructor injectableConstructor = constructor;
        HashMap<Class, Object> parameters = new HashMap<Class, Object>();
        for (Class type : constructor.getParameterTypes()) {
            DSBinding bind = GraphHelper.getInstance().findBinding(type, binders);
            if (bind != null) { //injectable type and found in bindings
                try {
                    parameters.put(type, bind.getSpecificInstance());
                } catch (Exception ex) {
                    Logger.getLogger(InjectHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (injectableConstructor != null) {
            try {
                ret = injectableConstructor.newInstance(parameters.values().toArray());
            } catch (Exception ex) {
                Logger.getLogger(InjectHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
    /**
     * Injection on fields.
     * @param obj injected object.
     * @param field concerned fields.
     * @param binders concerned binders.
     * @return the injected object.
     */
    public Object injectField(final Object obj, final Field field, final Vector<DSBinder> binders) {
        Object ret = obj;
        DSBinding bind = GraphHelper.getInstance().findBinding(field.getType(), binders);
        if (bind != null) { //injectable type and found in bindings
            try {
                field.setAccessible(true);
                field.set(ret, bind.getSpecificInstance());
                field.setAccessible(false);
            } catch (Exception ex) {
                Logger.getLogger(InjectHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }
    /**
     * Injection on methods.
     * @param obj injected object.
     * @param method concerned method.
     * @param binders concerned binders.
     * @return the injected object.
     */
    public Object injectMethod(final Object obj, final Method method, final Vector<DSBinder> binders) {
        Object ret = null;
        HashMap<Class, Object> parameters = new HashMap<Class, Object>();
        for (Class type : method.getParameterTypes()) { // check for param annotation
            DSBinding bind = GraphHelper.getInstance().findBinding(type, binders);
            if (bind != null) { //injectable type and found in bindings
                try {
                    parameters.put(type, bind.getSpecificInstance());
                } catch (Exception ex) {
                    Logger.getLogger(InjectHandler.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        try {
            method.invoke(obj, parameters.values().toArray());
            ret = obj;
        } catch (Exception ex) {
            Logger.getLogger(InjectHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }
}
