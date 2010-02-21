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
import cx.ath.mancel01.dependecyshot.web.controller.ControllerServlet;
import cx.ath.mancel01.dependencyshot.samples.vdm.model.Vdm;
import cx.ath.mancel01.dependencyshot.samples.vdm.service.RandomService;
import javax.inject.Inject;

/**
 * Controller servlet for the VDM webapp.
 *
 * @author Mathieu ANCELIN
 */
public class VdmController extends ControllerServlet {
    /**
     * The random VDM service.
     */
    @Inject
    private RandomService service;
    /**
     * Current model of the servlet.
     */
    private Vdm model;
    /**
     * The view of the controller
     */
    private static final String VIEW = "/vdm.jsp";
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
    public String view() {
        return VIEW;
    }
    /**
     * Update the model.
     * 
     * @return the new model.
     */
    @Attribute(name="vdm")
    public Vdm getRandomVdm() {
        updateModel();
        return model;
    }
}
