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
package cx.ath.mancel01.dependencyshot.samples.vdm.service.impl;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import cx.ath.mancel01.dependencyshot.samples.vdm.config.VdmUrl;
import cx.ath.mancel01.dependencyshot.samples.vdm.model.Random;
import cx.ath.mancel01.dependencyshot.samples.vdm.model.Vdm;
import cx.ath.mancel01.dependencyshot.samples.vdm.service.RandomService;
import cx.ath.mancel01.dependencyshot.utils.annotations.Log;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * The implementation of the random service.
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public class RandomServiceImpl implements RandomService {

    /**
     * Jersey REST resource.
     */
    private WebResource restResource;

    /**
     * The logger of the service;
     */
    @Inject @Log
    private Logger logger;

    /**
     * Creation of the jersey client.
     *
     * @param restServerUrl the URL of the service.
     */
    @Inject
    public final void setRestServerUrl(@VdmUrl String restServerUrl) {
        this.restResource = Client.create().resource(restServerUrl);
    }

    /**
     * @{@inheritDoc }
     */
    @Override
    public final Vdm getRandomVdm() {
        logger.info("Get a new VDM from VDM REST service");
        Vdm model = null;
        try {
            model = (Vdm) restResource.path("view").path("random")
                    .get(Random.class).getVdms().toArray()[0];
        } catch (Exception e) {
            logger.warning(e.getMessage());
        }
        return model;
    }
}
