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

package cx.ath.mancel01.dependecyshot.web.binders;

import cx.ath.mancel01.dependecyshot.web.annotations.JndiLookup;
import cx.ath.mancel01.dependecyshot.web.annotations.WebProperty;
import cx.ath.mancel01.dependecyshot.web.providers.JndiProvider;
import cx.ath.mancel01.dependecyshot.web.providers.WebPropertiesProvider;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.graph.Binder;

/**
 *
 * @author Mathieu ANCELIN
 */
public class WebBinder  extends Binder {

    @Override
    public void configureBindings() {
        DSInjector injector = this.getBinderInjector();
        bind(String.class).annotedWith(WebProperty.class).providedBy(new WebPropertiesProvider(injector));
        bind(String.class).annotedWith(JndiLookup.class).providedBy(new JndiProvider());
    }
}
