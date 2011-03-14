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

package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.aop.annotation.Decorator;
import cx.ath.mancel01.dependencyshot.aop.annotation.Delegate;
import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 *
 * @author Mathieu ANCELIN
 */
public class DecoratedBinder<T> {

    private final AOPBinder binder;

    private final Class<T> decorated;

    public DecoratedBinder(AOPBinder binder, Class<T> decorated) {
        this.binder = binder;
        this.decorated = decorated;
    }

    public void with(Class<? extends T> decorator) {
        if (decorator.isAnnotationPresent(Decorator.class)) {
            boolean notExtends = false;
            for (Class clazz : decorator.getInterfaces()) {
                if (clazz.equals(decorated)) {
                    notExtends = true;
                }
            }
            if (!notExtends) {
                throw new DSIllegalStateException("Your decorator must implements at least one common interface with decorated class.");
            }
            if (!containsDelegate(decorator)) { // TODO : only one delegate
                throw new DSIllegalStateException("Your decorator must have a @Delegate injection point.");
            }
            binder.with(decorated, decorator);
        } else {
            throw new DSIllegalStateException("You try to decorate a type with a non decorator class !!!");
        }
    }

    private boolean containsDelegate(Class clazz) {
        for (Constructor construct : clazz.getConstructors()) {
            if (construct.isAnnotationPresent(Delegate.class)) {
                return true;
            }
        }
        for (Constructor construct : clazz.getDeclaredConstructors()) {
            if (construct.isAnnotationPresent(Delegate.class)) {
                return true;
            }
        }
        for (Field field : clazz.getFields()) {
            if (field.isAnnotationPresent(Delegate.class)) {
                return true;
            }
        }
        for (Field field : clazz.getDeclaredFields()) {
            if (field.isAnnotationPresent(Delegate.class)) {
                return true;
            }
        }
        for (Method method : clazz.getMethods()) {
            Class<?>[] types = method.getParameterTypes();
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int j = 0; j < types.length; j++) {
                for (Annotation anno : annotations[j]) {
                    if (anno.annotationType().equals(Delegate.class)) {
                        return true;
                    }
                }
            }
        }
        for (Method method : clazz.getDeclaredMethods()) {
            Class<?>[] types = method.getParameterTypes();
            Annotation[][] annotations = method.getParameterAnnotations();
            for (int j = 0; j < types.length; j++) {
                for (Annotation anno : annotations[j]) {
                    if (anno.annotationType().equals(Delegate.class)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
