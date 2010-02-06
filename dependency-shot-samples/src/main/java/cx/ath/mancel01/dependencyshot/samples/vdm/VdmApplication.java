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

package cx.ath.mancel01.dependencyshot.samples.vdm;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.samples.vdm.config.VdmGuiBinder;
import cx.ath.mancel01.dependencyshot.samples.vdm.config.VdmServiceBinder;
import cx.ath.mancel01.dependencyshot.samples.vdm.gui.impl.VdmController;
import cx.ath.mancel01.dependencyshot.samples.vdm.gui.View;
import javax.inject.Inject;

/**
 * Bootstrap class of the application
 *
 * @author Mathieu ANCELIN
 */
public class VdmApplication {

    /**
     * The controller of the app.
     */
    @Inject
    private VdmController controller;

    /**
     * The view of the app.
     */
    @Inject
    private View view1;

    /**
     * Another view for fun.
     */
    @Inject
    private View view2;

    /**
     * Application startup.
     */
    protected final void startup() {
        view1.start();
        view2.start();
        controller.startApplication();
    }

    /**
     * Main method with bootstrapping of the application and startup.
     *
     * @param args launch arguments.
     */
    public static void main(String[] args) {
        DSInjector injector = DependencyShot.getInjector(new VdmServiceBinder(), new VdmGuiBinder());
        VdmApplication app = injector.getInstance(VdmApplication.class);
        app.startup();
    }
}
