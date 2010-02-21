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
import cx.ath.mancel01.dependecyshot.web.listeners.DependencyShotListener;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.annotations.InjectLogger;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.logging.Logger;
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
    /**
     * The logger of the controller;
     */
    @Inject
    @InjectLogger
    protected Logger logger;
    /**
     * {@inheritDoc }
     */
    @Override
    public void init() throws ServletException {
        DSInjector injector = (DSInjector) getServletContext()
                .getAttribute(DependencyShotListener.INJECTOR_NAME);
        injector.injectInstance(this);
        logger.info("The controller is now weaved");
    }
    /**
     * {@inheritDoc }
     */
    @Override
    public void destroy() {
        super.destroy();
        logger.info("Destruction of the controller");
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
                Object ret = null;
                String name = method.getAnnotation(Attribute.class).name();
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
                boolean accessible = method.isAccessible();
                if (!accessible) {
                    method.setAccessible(true);
                }
                try {
                    ret = method.invoke(this, parameters);
                } catch(Exception e) {
                    logger.severe(e.toString());
                } finally {
                    if (!accessible) {
                        method.setAccessible(accessible);
                    }
                }
                // TODO : add a proxy here or something like that to lazyload
                request.setAttribute(name, ret);
            }
        }
    }
}
