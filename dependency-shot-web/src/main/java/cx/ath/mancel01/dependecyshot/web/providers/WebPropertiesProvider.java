/*
 *  Copyright 2009-2010 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependecyshot.web.providers;

import cx.ath.mancel01.dependecyshot.web.annotations.WebProperty;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.injection.util.EnhancedProvider;
import java.lang.annotation.Annotation;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Built-in provider for properties injection in web bridge.
 *
 * @author Mathieu ANCELIN
 */
public class WebPropertiesProvider implements EnhancedProvider {

    private static final String PROP_FILE_PREFIX = "";
    private static final String PROP_FILE_SUFFIX = ".properties";
    private static final String NOT_INJECTED = "NOT INJECTED";
    private Stage stage = null;

    private WebPropertiesProvider() { }

    public WebPropertiesProvider(DSInjector injector) {
        this.stage = injector.getStage();
    }

    @Override
    public final Object enhancedGet(InjectionPoint p) {
        String value = NOT_INJECTED;
        for (Annotation qualifier : p.getAnnotations()) {
            if (qualifier instanceof WebProperty) {
                WebProperty prop = (WebProperty) qualifier;
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
    public final Object get() {
        throw new UnsupportedOperationException(
                "This operation isn't supported on EnhancedProvider.");
    }

    private String getProperty(
            String key, String defaultValue,
            Class clazz, String name) {
        try {
            Properties props = new Properties();
            props.load(clazz.getClassLoader()
                    .getResourceAsStream(createPath(name, clazz)));
            String ret = NOT_INJECTED;
            if (stage != null) {
                key += "." + stage.name().toLowerCase();
            }
            if (defaultValue.equals("")) {
                ret = props.getProperty(key);
            } else {
                ret = props.getProperty(key, defaultValue);
            }
            if (ret == null) {
                Logger.getLogger(WebPropertiesProvider.class.getName())
                        .log(Level.WARNING, "Can't find property with key \""
                        + key
                        + "\". The value \""
                        + NOT_INJECTED
                        + "\" is injected instead.");
                return NOT_INJECTED;
            }
            return ret;
        } catch (Exception ex) {
            Logger.getLogger(WebPropertiesProvider.class.getName())
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
