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
 *
 * @author Mathieu ANCELIN
 */
public class AnnotationsProcessor {

    /**
     * The unique instance of the class
     **/
    private static AnnotationsProcessor INSTANCE = null;

    /**
     * The private constructor of the singleton
     **/
    private AnnotationsProcessor() {
    }

    /**
     * The accessor for the unique instance of the singleton
     **/
    public static synchronized AnnotationsProcessor getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AnnotationsProcessor();
        }
        return INSTANCE;
    }

    public Object processClassAnnotations(Class clazz, Vector<DSBinder> binders) {
        return processMethodsAnnotations(
                processFieldsAnnotations(
                processConstructorAnnotations(clazz, binders),
                binders),
                binders);
    }

    public Object processInstanceAnnotations(Object obj, Vector<DSBinder> binders) {
        return processMethodsAnnotations(
                processFieldsAnnotations(obj, binders),
                binders);
    }

    public Object processInstanceFieldsAnnotations(Object obj, Vector<DSBinder> binders) {
        return processFieldsAnnotations(obj, binders);
    }

    public Object processInstanceMethodAnnotations(Object obj, Vector<DSBinder> binders) {
        return processMethodsAnnotations(obj, binders);
    }

    private Object processConstructorAnnotations(Class clazz, Vector<DSBinder> binders) {
        boolean injectFound = false;
        Object ret = null;
        Constructor[] constructors = clazz.getConstructors();
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

    private Object processFieldsAnnotations(Object obj, Vector<DSBinder> binders) {
        Object ret = null;
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Inject.class)) {
                ret = InjectHandler.getInstance().injectField(obj, field, binders);
            }
        }
        return ret;
    }

    private Object processMethodsAnnotations(Object obj, Vector<DSBinder> binders) {
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
