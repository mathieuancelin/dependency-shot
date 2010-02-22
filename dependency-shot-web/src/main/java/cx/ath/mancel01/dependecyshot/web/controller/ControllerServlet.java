/*
 *  Copyright 2010 Mathieu ANCELIN.
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
package cx.ath.mancel01.dependecyshot.web.controller;

import cx.ath.mancel01.dependecyshot.web.annotations.Attribute;
import cx.ath.mancel01.dependecyshot.web.handlers.ELMethodHandler;
import cx.ath.mancel01.dependecyshot.web.listeners.DependencyShotListener;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.annotations.InjectLogger;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.ClassClassPath;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Abstract controller to write less code in your webapps :)
 *
 * @author Mathieu ANCELIN
 */
public abstract class ControllerServlet extends HttpServlet {
    private static final String DS_PREFIX = "cx.ath.mancel01.depshot.generated.Model";
    private static final String OBJECT = "java.lang.Object";
    /**
     * The logger of the controller;
     */
    @Inject
    @InjectLogger
    protected Logger logger;

    /**
     * Collection of the managed gnerated interfaces for models.
     */
    private Map<String, Class> managedItfClasses = new HashMap<String, Class>();

    /**
     * {@inheritDoc }
     */
    @Override
    public void init() throws ServletException {
        DSInjector injector = (DSInjector) getServletContext()
                .getAttribute(DependencyShotListener.INJECTOR_NAME);
        injector.injectInstance(this);
        
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public void destroy() {
        
        super.destroy();
    }

    /**
     * Getter on the controller's view.
     *
     * @return the controller's view.
     */
    public abstract String view();

    /**
     * Dispatch the input request to the right view.
     *
     * @param view name of the view.
     * @param request web request.
     * @param response web response.
     * @throws ServletException exception.
     * @throws IOException exception.
     */
    private void goToView(String view, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher(view).forward(request, response);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        registerAttributes(request, response);
        goToView(view(), request, response);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        registerAttributes(request, response);
        goToView(view(), request, response);
    }

    /**
     * This method invoke each @Attribute method in the controller
     * so they can register models values in the request.
     *
     * @param request web request.
     * @param response web response.
     */
    private void registerAttributes(HttpServletRequest request, HttpServletResponse response) {
        Class clazz = this.getClass();
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Attribute.class)) {
                String name = method.getAnnotation(Attribute.class).value();
                Class<?>[] parameterTypes = method.getParameterTypes();
                Object[] parameters = new Object[parameterTypes.length];
                for (int j = 0; j < parameterTypes.length; j++) {
                    if (parameterTypes[j].equals(HttpServletRequest.class)) {
                        parameters[j] = request;
                    } else if (parameterTypes[j].equals(HttpServletResponse.class)) {
                        parameters[j] = response;
                    } else if (parameterTypes[j].equals(HttpSession.class)) {
                        parameters[j] = request.getSession();
                    } else {
                        parameters[j] = null;
                    }
                }
                try {
                    request.setAttribute(name, getProxy(method, parameters));
                } catch (Exception ex) {
                    Logger.getLogger(ControllerServlet.class.getName())
                            .log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * 
     * @param method
     * @param parameters
     * @return
     * @throws Exception
     */
    private Object getProxy(Method method, Object[] parameters) throws Exception {
        ELMethodHandler handler = new ELMethodHandler(method, this, parameters);
        ClassPool pool = ClassPool.getDefault();
        pool.insertClassPath(new ClassClassPath(this.getClass()));
        CtClass modelInterface = null;
        String itfKey = DS_PREFIX + method.getReturnType().getSimpleName();
        if (!managedItfClasses.containsKey(itfKey)) {
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
            managedItfClasses.put(itfKey,
                    modelInterface.toClass());
        }
        return Proxy.newProxyInstance(
                Thread.currentThread().getContextClassLoader(),
                new Class[]{managedItfClasses.get(itfKey)},
                handler);
    }
}
