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
package cx.ath.mancel01.dependencyshot.samples.vdm.web;

import cx.ath.mancel01.dependecyshot.web.annotations.Attribute;
import cx.ath.mancel01.dependecyshot.web.annotations.Destroy;
import cx.ath.mancel01.dependecyshot.web.annotations.Init;
import cx.ath.mancel01.dependecyshot.web.annotations.WebProperty;
import cx.ath.mancel01.dependecyshot.web.controller.ControllerServlet;
import cx.ath.mancel01.dependencyshot.samples.vdm.model.Vdm;
import cx.ath.mancel01.dependencyshot.samples.vdm.service.RandomService;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

/**
 * Controller servlet for the VDM webapp.
 *
 * @author Mathieu ANCELIN
 */
public class VdmController extends ControllerServlet {
    /**
     * Current model of the servlet.
     */
    private Vdm model;
    /**
     * The random VDM service.
     */
    @Inject private RandomService service;
    /**
     * The view of the controller
     */
    @Inject @WebProperty private String view;
    /**
     * The title of the webapp.
     */
    @Inject @WebProperty private String title;
    /**
     * The author of the vdm.
     */
    @Inject @WebProperty private String author;
    /**
     * The label on the button.
     */
    @Inject @WebProperty private String button;
    /**
     * The default text of the webapp.
     */
    @Inject @WebProperty private String defaultText;
    /**
     * Update the current model.
     */
    private void updateModel() {
        model = service.getRandomVdm();
        logger.info(model.toString());
    }
    /**
     * @return the view of this controller.
     */
    @Override
    public final String view() {
        return view;
    }
    /**
     * Update the model.
     * 
     * @return the new model.
     */
    @Attribute("vdm")
    public final Vdm getRandomVdm(HttpServletRequest request) {
        logger.info(request.getRequestURI());
        updateModel();
        return model;
    }

    @Attribute("title")
    public final String getTitle() {
        return title;
    }

    @Attribute("author")
    public final String getAuthor() {
        return author;
    }

    @Attribute("button")
    public final String getButton() {
        return button;
    }

    @Attribute("defaultText")
    public final String getDefaultText() {
        return defaultText;
    }

    @Init
    private void postConstruct() {
        logger.info("post construct");
    }

    @Destroy
    private void preDestroy() {
        logger.info("pre destroy");
    }
}
