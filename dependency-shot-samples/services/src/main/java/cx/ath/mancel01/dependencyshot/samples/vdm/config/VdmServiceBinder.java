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

package cx.ath.mancel01.dependencyshot.samples.vdm.config;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.samples.vdm.service.RandomService;
import cx.ath.mancel01.dependencyshot.samples.vdm.service.impl.RandomServiceImpl;

/**
 * Binder for the services part of the app.
 *
 * @author Mathieu ANCELIN
 */
public class VdmServiceBinder extends Binder {

    /**
     * Configuration of the services module.
     */
    @Override
    public final void configureBindings() {
        bind(String.class).annotatedWith(VdmUrl.class).toInstance("http://api.viedemerde.fr/1.0/");
        bind(RandomService.class).to(RandomServiceImpl.class);
    }
}
