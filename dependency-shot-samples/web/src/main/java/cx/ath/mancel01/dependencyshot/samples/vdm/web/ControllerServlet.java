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
package cx.ath.mancel01.dependencyshot.samples.vdm.web;

import cx.ath.mancel01.dependecyshot.web.listeners.DependencyShotListener;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.annotations.InjectLogger;
import cx.ath.mancel01.dependencyshot.samples.vdm.model.Vdm;
import cx.ath.mancel01.dependencyshot.samples.vdm.service.RandomService;
import java.io.IOException;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller servlet for the VDM webapp.
 *
 * @author mathieuancelin
 */
public class ControllerServlet extends HttpServlet {
    /**
     * The random VDM service.
     */
    @Inject
    private RandomService service;
    /**
     * The logger of the controller;
     */
    @Inject
    @InjectLogger
    private Logger logger;
    /**
     * Current model of the servlet.
     */
    private Vdm model;
    /**
     * Initialization of the servlet.
     * Injection of the servlet.
     */
    @Override
    public void init() {
        DSInjector injector = (DSInjector) getServletContext()
                .getAttribute(DependencyShotListener.INJECTOR_NAME);
        injector.injectInstance(this);
    }
    /**
     * Update the current model.
     */
    private void updateModel() {
        model = service.getRandomVdm();
        logger.info(model.toString());
    }
    /**
     * Handles the HTTP <code>GET</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("View on the webapp");
        updateModel();
        request.setAttribute("vdmauthor", model.getAuthor());
        request.setAttribute("vdmtext", model.getText());
        getServletContext().getRequestDispatcher("/vdm.jsp").forward(request, response);
    }
    /** 
     * Handles the HTTP <code>POST</code> method.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        logger.info("Get a random VDM");
        updateModel();
        request.setAttribute("vdmauthor", model.getAuthor());
        request.setAttribute("vdmtext", model.getText());
        getServletContext().getRequestDispatcher("/vdm.jsp").forward(request, response);
    }
    /** 
     * Returns a short description of the servlet.
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Controller of the VDM webapp";
    }
}
