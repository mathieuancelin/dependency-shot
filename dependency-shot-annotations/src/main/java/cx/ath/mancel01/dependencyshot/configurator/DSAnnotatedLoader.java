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
package cx.ath.mancel01.dependencyshot.configurator;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu ANCELIN
 */
public class DSAnnotatedLoader {

    private static final Logger logger = Logger.getLogger(DSAnnotatedLoader.class.getSimpleName());

    public static Collection<Class<?>> loadNamed(String packagePrefix) {
        return load("named.annotated", packagePrefix);
    }

    public static Collection<Class<?>> loadSingletons(String packagePrefix) {
        return load("singleton.annotated", packagePrefix);
    }

    public static Collection<Class<?>> loadManagedBeans(String packagePrefix) {
        return load("managed.annotated", packagePrefix);
    }

    private static Collection<Class<?>> load(String filename, String packagePrefix) {
        Collection<Class<?>> services = new ArrayList<Class<?>>();
        try {
            ClassLoader classLoader = DSAnnotatedLoader.class.getClassLoader();
                    //Thread.currentThread().getContextClassLoader();
            Enumeration<URL> e = classLoader.getResources(filename);
            while (e.hasMoreElements()) {
                URL url = e.nextElement();
                InputStream is = url.openStream();
                try {
                    BufferedReader r = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                    while (true) {
                        String line = r.readLine();
                        if (line == null) {
                            break;
                        }
                        String name = line.trim();
                        if (name.length() == 0) {
                            continue;
                        }
                        try {
                            Class<?> clz = Class.forName(name, true, classLoader);
                            if (packagePrefix != null && packagePrefix.length() > 0) {
                                if(clz.getPackage().getName().startsWith(packagePrefix)) {
                                    services.add(clz);
                                }
                            } else {
                                services.add(clz);
                            }
                        } catch (ClassNotFoundException ex) {}
                    }
                } finally {
                    is.close();
                }
            }
        } catch (Exception e) {
            logger.log(Level.WARNING, "Error while reading {0}", filename);
        }
        return services;
    }
}
