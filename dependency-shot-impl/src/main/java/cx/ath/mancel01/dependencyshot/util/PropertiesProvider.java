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

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.api.annotations.Property;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu ANCELIN
 */
public class PropertiesProvider implements EnhancedProvider {

    private static final String PROP_FILE_PREFIX = "META-INF/";
    private static final String PROP_FILE_SUFFIX = ".properties";
    private static final String NOT_INJECTED = "NOT INJECTED";

    @Override
    public Object enhancedGet(InjectionPoint p) {
        String value = NOT_INJECTED;
        for (Annotation qualifier : p.getAnnotations()) {
            if (qualifier instanceof Property) {
                Property prop = (Property) qualifier;
                String key = prop.name();
                if (key.equals("")) {
                    key = p.getMember().getName();
                }
                value = getProperty(key, prop.value(),
                        p.getBeanClass(), prop.bundle());
                break;
            }
        }
        return value;
    }

    @Override
    public Object get() {
        throw new UnsupportedOperationException("This operation isn't supported.");
    }

    private String getProperty(
            String key, String defaultValue,
            Class clazz, String name) {
        try {
            Properties props = new Properties();
            props.load(clazz.getClassLoader()
                    .getResourceAsStream(createPath(name, clazz)));
            if (defaultValue.equals("")) {
                return props.getProperty(key);
            } else {
                return props.getProperty(key, defaultValue);
            }
        } catch (Exception ex) {
            Logger.getLogger(PropertiesProvider.class.getName())
                    .log(Level.SEVERE, null, ex);
            return NOT_INJECTED;
        }
    }

    private String createPath(String name, Class clazz) {
        if (!name.equals("") && clazz != null) {
            return PROP_FILE_PREFIX + name + PROP_FILE_SUFFIX;
        } else {
            return PROP_FILE_PREFIX +
                    clazz.getSimpleName().toLowerCase() + PROP_FILE_SUFFIX;
        }
    }
}
