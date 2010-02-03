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

package cx.ath.mancel01.dependencyshot.injection.handlers;

import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import javax.inject.Inject;

/**
 * Utility class that handle injection on the fields of a class.
 *
 * @author Mathieu ANCELIN
 */
public final class FieldsHandler {

    private FieldsHandler() {}
    /**
     * Inject every fields of an object.
     * 
     * @param <T> the type of the injected object.
     * @param instance the injected object.
     * @param c the class of the object.
     * @param staticInjection can we inject static fields.
     * @param injector the concerned injector.
     * @throws IllegalAccessException
     */
    public static <T> void fieldsInjection(T instance, Class<?> c,
            boolean staticInjection,
            InjectorImpl injector) throws IllegalAccessException {
		Field[] fieldsOfTheClass = c.getDeclaredFields();
        // for each declared fields
		for(Field field : fieldsOfTheClass) {
			Inject annotation = field.getAnnotation(Inject.class);
            // check if field is injectable and if you can inject static fields
			if (annotation != null &&
                    (staticInjection == Modifier.isStatic(field.getModifiers()))) {
                // check if the field is not final
				if (Modifier.isFinal(field.getModifiers())) {
					throw new DSIllegalStateException("Cannot inject final field: " + field);
				}
				Class<?> type = field.getType();
				Type genericType = field.getGenericType();
                // get an instance of the field (simple instance or provided one
				Object injectedObject = injector.getProviderOrInstance(type, genericType, field.getAnnotations());
				boolean accessible = field.isAccessible();
                // if field is private then put it private for injectedObject setting
				if (!accessible) {
					field.setAccessible(true);
				}
				try {
					field.set(instance, injectedObject);
				} finally {
                    // if the field was private, then put it private back
					if (!accessible) {
						field.setAccessible(accessible);
					}
				}
			}
		}
	}
}
