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
}
