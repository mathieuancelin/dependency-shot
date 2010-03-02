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
package cx.ath.mancel01.dependecyshot.web.handlers;

import cx.ath.mancel01.dependecyshot.web.annotations.Destroy;
import cx.ath.mancel01.dependecyshot.web.annotations.Init;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;

/**
 *
 * @author Mathieu ANCELIN
 */
public class LifecycleHandler {

    public final void fireInits(Object caller, ServletConfig cfg) {
        for (Method method : caller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Init.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for (int j = 0; j < parameterTypes.length; j++) {
                    if (parameterTypes[j].equals(ServletConfig.class)) {
                        parameters[j] = cfg;
                    } else {
                        parameters[j] = null;
                    }
                }
                boolean accessible = method.isAccessible();
                if (!accessible) {
                    method.setAccessible(true);
                }
                try {
                    method.invoke(caller, parameters);
                } catch (Exception ex) {
                    Logger.getLogger(LifecycleHandler.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (!accessible) {
                        method.setAccessible(accessible);
                    }
                }
            }
        }
    }

    public final void fireDestroys(Object caller) {
        for (Method method : caller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Destroy.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for (int j = 0; j < parameterTypes.length; j++) {
                    parameters[j] = null;
                }
                boolean accessible = method.isAccessible();
                if (!accessible) {
                    method.setAccessible(true);
                }
                try {
                    method.invoke(caller, parameters);
                } catch (Exception ex) {
                    Logger.getLogger(LifecycleHandler.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    if (!accessible) {
                        method.setAccessible(accessible);
                    }
                }
            }
        }
    }
}
