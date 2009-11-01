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
package cx.ath.mancel01.dependencyshot.injection;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.injection.handlers.InjectHandler;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Vector;
import javax.inject.Inject;

/**
 * The goal of this class is to navigate through annotations
 * and inject depedencies.
 * 
 * @author Mathieu ANCELIN
 */
public final class AnnotationsProcessor {

    /**
     * The unique InjectHandler of the class.
     */
    private static AnnotationsProcessor instance = null;

    /**
     * The private constructor of the singleton.
     */
    private AnnotationsProcessor() {
    }

    /**
     * The accessor for the unique InjectHandler of the singleton.
     * @return the unique InjectHandler of the singleton.
     */
    public static synchronized AnnotationsProcessor getInstance() {
        if (instance == null) {
            instance = new AnnotationsProcessor();
        }
        return instance;
    }
    /**
     * Process all annotations on a class.
     * @param clazz the concerned class.
     * @param binders concerned binders.
     * @return the injected object.
     */
    public Object processClassAnnotations(final Class clazz, final Vector<DSBinder> binders) {
        return processMethodsAnnotations(
                processFieldsAnnotations(
                processConstructorAnnotations(clazz, binders),
                binders),
                binders);
    }
    /**
     * Process all annotations on an object.
     * @param obj the concerned object.
     * @param binders concerned binders.
     * @return the injected object.
     */
    public Object processInstanceAnnotations(final Object obj, final Vector<DSBinder> binders) {
        return processMethodsAnnotations(
                processFieldsAnnotations(obj, binders),
                binders);
    }
    /**
     * Process field annotations on an object.
     * @param obj the concerned object.
     * @param binders concerned binders.
     * @return the injected object.
     */
    public Object processInstanceFieldsAnnotations(final Object obj, final Vector<DSBinder> binders) {
        return processFieldsAnnotations(obj, binders);
    }
    /**
     * Process method annotations on a an object.
     * @param obj the concerned object.
     * @param binders concerned binders.
     * @return the injected object.
     */
    public Object processInstanceMethodAnnotations(final Object obj, final Vector<DSBinder> binders) {
        return processMethodsAnnotations(obj, binders);
    }
    /**
     * Process constructor annotations on a class.
     * @param clazz the concerned class.
     * @param binders concerned binders.
     * @return the injected object.
     */
    private Object processConstructorAnnotations(final Class clazz, final Vector<DSBinder> binders) {
        boolean injectFound = false;
        Object ret = null;
        Constructor[] constructors = clazz.getDeclaredConstructors();//getConstructors();
        for (Constructor constructor : constructors) {
            if (constructor.isAnnotationPresent(Inject.class) && !injectFound) {
                injectFound = true;
                ret = InjectHandler.getInstance().injectConstructor(constructor, binders);
            }
            if (ret == null) {
                injectFound = false;
            }
        }
        return ret;
    }
    /**
     * Process field annotations on a an object.
     * @param obj the concerned object.
     * @param binders concerned binders.
     * @return the injected object.
     */
    private Object processFieldsAnnotations(final Object obj, final Vector<DSBinder> binders) {
        Object ret = null;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                ret = InjectHandler.getInstance().injectField(obj, field, binders);
            }
        }
        return ret;
    }
    /**
     * Process method annotations on a an object.
     * @param obj the concerned object.
     * @param binders concerned binders.
     * @return the injected object.
     */
    private Object processMethodsAnnotations(final Object obj, final Vector<DSBinder> binders) {
        Object ret = null;
        Method[] methods = obj.getClass().getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Inject.class)) {
                ret = InjectHandler.getInstance().injectMethod(obj, method, binders);
            }
        }
        return ret;
    }
}
