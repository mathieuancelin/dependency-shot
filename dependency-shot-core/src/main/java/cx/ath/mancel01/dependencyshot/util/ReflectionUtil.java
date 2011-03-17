/*
 *  Copyright 2010 mathieuancelin.
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
package cx.ath.mancel01.dependencyshot.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Set;
import javax.inject.Named;
import javax.inject.Qualifier;
import javax.inject.Scope;
import javax.inject.Singleton;

/**
 *
 * @author mathieuancelin
 */
public class ReflectionUtil {

    public static boolean isSingleton(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Singleton.class)) {
            return true;
        }
        return false;
    }

    public static Class<? extends Annotation> getScope(Class clazz) {
        Annotation[] annotations = clazz.getAnnotations();
        Class<? extends Annotation> scope = null;
        for (Annotation anno : annotations) {
            if ((anno.annotationType().isAnnotationPresent(Scope.class))) {
                scope = anno.annotationType();
            }
        }
        return scope;
    }

    public static Annotation getQualifier(Set<Annotation> annotations) {
        Annotation annotation = null;
        for (Annotation anno : annotations) {
            if ((anno.annotationType().isAnnotationPresent(Qualifier.class))) {
                annotation = anno;
            }
        }
        return annotation;
    }

    public static Named getNamed(Set<Annotation> annotations) {
        Named annotation = null;
        for (Annotation anno : annotations) {
            if ((anno.annotationType().equals(Named.class))) {
                annotation = (Named) anno;
            }
        }
        return annotation;
    }

    public static Object getProxyFor(InvocationHandler handler, Class... interfaces) {
         return Proxy.newProxyInstance(
                ReflectionUtil.class.getClassLoader(),
                interfaces, handler);
    }

    public static void setField(Field field, Object instance, Object value) 
            throws IllegalArgumentException, IllegalAccessException {
        boolean accessible = field.isAccessible();
        // if field is private then put it private for injectedObject setting
        if (!accessible) {
            field.setAccessible(true);
        }
        try {
            field.set(instance, value);
        } finally {
            // if the field was private, then put it private back
            if (!accessible) {
                field.setAccessible(accessible);
            }
        }
    }

    public static Object invokeMethod(Method method, Object instance, Object... parameters)
            throws IllegalAccessException,
                IllegalArgumentException, InvocationTargetException {
        boolean accessible = method.isAccessible();
        // set a private method as public method to invoke it
        if (!accessible) {
            method.setAccessible(true);
        }
        // invocation of the method with rights parameters
        try {
            return method.invoke(instance, parameters);
        } finally {
            // if method was private, then put it private back
            if (!accessible) {
                method.setAccessible(accessible);
            }
        }
    }

    public static <T> T invokeConstructor(Constructor constructor, Class<T> type, Object... arguments) 
            throws InstantiationException, IllegalAccessException,
                IllegalArgumentException, InvocationTargetException {
        boolean accessible = constructor.isAccessible();
        // if the constructor is private, then put it public for newinstance creation
        if (!accessible) {
            constructor.setAccessible(true);
        }
        // create new instance with the constructor
        try {
            return type.cast(constructor.newInstance(arguments));
        } finally {
            // if constructor was private, then put it private back
            if (!accessible) {
                constructor.setAccessible(accessible);
            }
        }
    }
    
    /**
     * Check if a method is overridden by another one contained in a list.
     *
     * @param method method to check.
     * @param maybeOverrides methods that can overrides method.
     * @return if the method is overridden.
     */
    public static boolean isOverridden(Method method, List<Method> maybeOverrides) {
        for (Method candidate : maybeOverrides) {
            if (isOverridden(method, candidate)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if a method is overriden by another one.
     *
     * @param method method to check.
     * @param candidate method to check against.
     * @return if the method is overridden.
     */
    public static boolean isOverridden(Method method, Method candidate) {
        // check f names are the same
        if (!method.getName().equals(candidate.getName())) {
            return false;
        }
        int modifiers = candidate.getModifiers();
        // check if candidate is private
        boolean isPrivate = Modifier.isPrivate(modifiers);
        if (isPrivate) {
            return false;
        }
        // check if candidate is static
        boolean isStatic = Modifier.isStatic(modifiers);
        if (isStatic) {
            return false;
        }
        boolean isDefault = !isPrivate
                && !Modifier.isPublic(modifiers)
                && !Modifier.isProtected(modifiers);
        boolean samePackage =
                method.getDeclaringClass().getPackage()
                == candidate.getDeclaringClass().getPackage();
        if (isDefault && !samePackage) {
            return false;
        }
        // check if parameters are the same
        Class<?>[] methodParameters = method.getParameterTypes();
        Class<?>[] candidateParameters = candidate.getParameterTypes();
        // check numbers of parameters
        if (methodParameters.length != candidateParameters.length) {
            return false;
        }
        // check types of parameters
        for (int i = 0; i < methodParameters.length; i++) {
            Class<?> class1 = methodParameters[i];
            Class<?> class2 = candidateParameters[i];
            if (!class1.equals(class2)) {
                return false;
            }
        }
        return true;
    }
}
