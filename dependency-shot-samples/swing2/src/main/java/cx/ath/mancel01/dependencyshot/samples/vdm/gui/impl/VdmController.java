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

package cx.ath.mancel01.dependencyshot.samples.vdm.gui.impl;

import cx.ath.mancel01.dependencyshot.samples.vdm.gui.Controller;
import cx.ath.mancel01.dependencyshot.samples.vdm.gui.View;
import cx.ath.mancel01.dependencyshot.samples.vdm.model.Vdm;
import cx.ath.mancel01.dependencyshot.samples.vdm.service.RandomService;
import cx.ath.mancel01.dependencyshot.utils.annotations.Log;
import java.util.Observable;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The controller implementation of the app.
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public class VdmController extends Observable implements Controller {

    /**
     * Model of the app.
     */
    private Vdm model;

    /**
     * The random VDM service.
     */
    @Inject
    private RandomService service;

    /**
     * The logger of the controller;
     */
    @Inject @Log
    private Logger logger;

    /**
     * The view attached to this controller
     */
    @Inject
    private View view;

    /**
     * Start the app.
     */
    public final void startApplication() {
        this.addObserver(view);
        updateModel();
        view.start();
        logger.info("Application now started ...");
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public final Vdm getModel() {
        return model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public final void updateModel() {
        this.model = this.service.getRandomVdm();
        logger.info("Model updated");
        this.setChanged();
        this.notifyObservers();  
    }
}
