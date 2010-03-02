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

import cx.ath.mancel01.dependecyshot.web.annotations.Requests;
import cx.ath.mancel01.dependecyshot.web.handlers.AttributeHandler;
import cx.ath.mancel01.dependecyshot.web.handlers.LifecycleHandler;
import cx.ath.mancel01.dependecyshot.web.handlers.RequestHandler;
import cx.ath.mancel01.dependecyshot.web.listeners.DependencyShotListener;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.annotations.InjectLogger;
import java.io.IOException;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     *
     */
    private AttributeHandler attributeHandler;

    /**
     * 
     */
    private LifecycleHandler lifecycleHandler;

    /**
     * 
     */
    private RequestHandler requestHandler;
    /**
     * {@inheritDoc }
     */
    @Override
    public final void init() throws ServletException {
        attributeHandler = new AttributeHandler();
        lifecycleHandler = new LifecycleHandler();
        requestHandler = new RequestHandler();
        DSInjector injector = (DSInjector) getServletContext()
                .getAttribute(DependencyShotListener.INJECTOR_NAME);
        injector.injectInstance(this);
        lifecycleHandler.fireInits(this, getServletConfig());
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void destroy() {
        lifecycleHandler.fireDestroys(this);
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
    public final void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestHandler.fireRequests(Requests.GET, this, request, response);
        registerAttributes(request, response);
        goToView(view(), request, response);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestHandler.fireRequests(Requests.POST, this, request, response);
        registerAttributes(request, response);
        goToView(view(), request, response);
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public final void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestHandler.fireRequests(Requests.DELETE, this, request, response);
        registerAttributes(request, response);
        goToView(view(), request, response);
    }
    
    /**
     * {@inheritDoc }
     */
    @Override
    public final void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        requestHandler.fireRequests(Requests.PUT, this, request, response);
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
        attributeHandler.registerAttributes(this.getClass(), this, request, response);
    }
}
