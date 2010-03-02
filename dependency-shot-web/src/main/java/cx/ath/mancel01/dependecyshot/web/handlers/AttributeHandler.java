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

import cx.ath.mancel01.dependecyshot.web.annotations.Attribute;
import cx.ath.mancel01.dependecyshot.web.cache.GeneratedItfCache;
import cx.ath.mancel01.dependecyshot.web.controller.ControllerServlet;
import cx.ath.mancel01.dependecyshot.web.el.ELLazyInvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

/**
 *
 * @author mathieuancelin
 */
public class AttributeHandler {

    /**
     * On the fly generated interfaces prefix.
     */
    private static final String DS_PREFIX = "cx.ath.mancel01.depshot.generated.Model";

    /**
     * Regular java object.
     */
    private static final String OBJECT = "java.lang.Object";

    /**
     * This method invoke each @Attribute method in the controller
     * so they can register models values in the request.
     *
     * @param request web request.
     * @param response web response.
     */
    public void registerAttributes(Class clazz, Object caller, HttpServletRequest request, HttpServletResponse response) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Attribute.class)) {
                String name = method.getAnnotation(Attribute.class).value();
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
                try {
                    request.setAttribute(name, getProxy(caller, method, parameters));
                } catch (Exception ex) {
                    Logger.getLogger(ControllerServlet.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Create a dynamic proxy that lazyload controller's attributes in the view.
     *
     * @param method
     * @param parameters
     * @return
     * @throws Exception
     */
    private Object getProxy( Object caller, Method method, Object[] parameters) throws Exception {
        ELLazyInvocationHandler handler = new ELLazyInvocationHandler(method, caller, parameters);
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(caller.getClass()));
        CtClass modelInterface = null;
        String itfKey = DS_PREFIX + method.getReturnType().getSimpleName();
        if (!GeneratedItfCache.getInstance().getManagedItfClasses()
                .containsKey(itfKey)) {
            modelInterface = pool.makeInterface(itfKey);
            CtClass modelClass = pool.get(method.getReturnType().getName());
            for (CtMethod meth : modelClass.getDeclaredMethods()) {
                if (meth.visibleFrom(pool.get(OBJECT))) {
                    CtMethod newMethod = CtNewMethod.abstractMethod(
                            meth.getReturnType(),
                            meth.getName(),
                            meth.getParameterTypes(),
                            meth.getExceptionTypes(),
                            modelInterface);
                    modelInterface.addMethod(newMethod);
                }
            }
            GeneratedItfCache.getInstance()
                    .getManagedItfClasses()
                    .put(itfKey, modelInterface.toClass());
        }
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{GeneratedItfCache
                        .getInstance()
                        .getManagedItfClasses()
                        .get(itfKey)},
                handler);
    }

}
