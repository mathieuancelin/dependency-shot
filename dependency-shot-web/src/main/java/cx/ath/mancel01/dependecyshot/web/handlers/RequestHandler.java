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
package cx.ath.mancel01.dependecyshot.web.handlers;

import cx.ath.mancel01.dependecyshot.web.annotations.OnRequest;
import cx.ath.mancel01.dependecyshot.web.annotations.Requests;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Mathieu ANCELIN
 */
public class RequestHandler {

    public final void fireRequests(Requests req, Object caller, HttpServletRequest request, HttpServletResponse response) {
        for (Method method : caller.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(OnRequest.class)) {
                Requests definedRequest = method.getAnnotation(OnRequest.class).value();
                if (definedRequest.equals(req)) {
                    Class<?>[] parameterTypes = method.getParameterTypes();
                    Object[] parameters = new Object[parameterTypes.length];
                    for (int j = 0; j < parameterTypes.length; j++) {
                        if (parameterTypes[j].equals(HttpServletRequest.class)) {
                            parameters[j] = new HttpServletRequestWrapper(request);
                        } else if (parameterTypes[j].equals(HttpServletResponse.class)) {
                            parameters[j] = new HttpServletResponseWrapper(response);
                        } else if (parameterTypes[j].equals(HttpSession.class)) {
                            parameters[j] = request.getSession();
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
                        Logger.getLogger(RequestHandler.class.getName()).log(Level.SEVERE, null, ex);
                    } finally {
                        if (!accessible) {
                            method.setAccessible(accessible);
                        }
                    }
                }
            }
        }
    }
}
