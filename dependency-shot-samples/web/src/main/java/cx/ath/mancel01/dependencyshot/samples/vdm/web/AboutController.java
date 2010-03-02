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

import cx.ath.mancel01.dependecyshot.web.annotations.Attribute;
import cx.ath.mancel01.dependecyshot.web.annotations.OnRequest;
import cx.ath.mancel01.dependecyshot.web.annotations.Requests;
import cx.ath.mancel01.dependecyshot.web.controller.ControllerServlet;
import cx.ath.mancel01.dependencyshot.api.annotations.Property;
import javax.inject.Inject;

/**
 *
 * @author mathieuancelin
 */
public class AboutController extends ControllerServlet {

    /**
     * The default text of the about page.
     */
    @Inject @Property private String aboutText;

    /**
     * The default text of the button.
     */
    @Inject @Property private String vdm;

    @Override
    public final String view() {
        return "/about.jsp";
    }

    @Attribute("about")
    private final String getAboutText() {
        return aboutText;
    }

    @Attribute("vdm")
    private final String getVdm() {
        return vdm;
    }

    @OnRequest(Requests.GET)
    private final void htmlGet() {
        logger.info("GET");
    }
}
