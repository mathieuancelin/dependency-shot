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
package cx.ath.mancel01.dependencyshot.injection.handlers;

import cx.ath.mancel01.dependencyshot.api.AnnotationHandler;
import cx.ath.mancel01.dependencyshot.api.IBinder;
import cx.ath.mancel01.dependencyshot.api.IBinding;
import cx.ath.mancel01.dependencyshot.graph.GraphHelper;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieuancelin
 */
public class InjectHandler implements AnnotationHandler {

    /**
     * The unique instance of the class
     **/
    private static InjectHandler INSTANCE = null;

    /**
     * The private constructor of the singleton
     **/
    private InjectHandler() {
    }

    /**
     * The accessor for the unique instance of the singleton
     **/
    public static synchronized InjectHandler getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InjectHandler();
        }
        return INSTANCE;
    }

    public Object injectConstructor(Constructor constructor, Vector<IBinder> binders) {
        Object ret = null;
        Constructor injectableConstructor = constructor;
        HashMap<Class, Object> parameters = new HashMap<Class, Object>(); // trouver solution pour plusieur injections de meme type
        for (Class type : constructor.getParameterTypes()) {
            IBinding bind = GraphHelper.getInstance().findBinding(type, binders);
            if (bind != null) { //injectable type and found in bindings
                try {
                    //System.out.println("contructor bind " + type.getName() + " to " + bind.getSpecificInstance().getClass().getName());
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

    public Object injectField(Object obj, Field field, Vector<IBinder> binders) {
        Object ret = obj;
        IBinding bind = GraphHelper.getInstance().findBinding(field.getType(), binders);
        if (bind != null) { //injectable type and found in bindings
            try {
                //System.out.println("field bind " + field.getType().getName() + " to " + bind.getSpecificInstance().getClass().getName());
                field.setAccessible(true);
                field.set(ret, bind.getSpecificInstance());
                field.setAccessible(false);
            } catch (Exception ex) {
                Logger.getLogger(InjectHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ret;
    }

    public Object injectMethod(Object obj, Method method, Vector<IBinder> binders) {
        Object ret = null;
        HashMap<Class, Object> parameters = new HashMap<Class, Object>();
        for(Class type : method.getParameterTypes()){ // check for param annotation
            IBinding bind = GraphHelper.getInstance().findBinding(type, binders);
            if (bind != null) { //injectable type and found in bindings
                try {
                    //System.out.println("method bind " + type.getName() + " to " + bind.getSpecificInstance().getClass().getName());
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
