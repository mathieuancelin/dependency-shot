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

package cx.ath.mancel01.dependencyshot.utils;

import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.graph.BindingBuilder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.BindingsProvider;
import cx.ath.mancel01.dependencyshot.utils.annotations.Log;
import cx.ath.mancel01.dependencyshot.utils.annotations.JndiLookup;
import cx.ath.mancel01.dependencyshot.utils.annotations.Property;
import cx.ath.mancel01.dependencyshot.utils.annotations.WebProperty;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu ANCELIN
 */
public class UtilsProvider extends BindingsProvider {

    @Override
    public Collection<Binding> getProvidedBindings(InjectorImpl injector) {

        ArrayList<Binding> bindings = new ArrayList<Binding>();

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(Logger.class)
                .annotatedWith(Log.class)
                .providedBy(new LoggerProvider())
                .build());

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(String.class)
                .annotatedWith(Property.class)
                .providedBy(new PropertiesProvider(injector))
                .build());

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(String.class)
                .annotatedWith(WebProperty.class)
                .providedBy(new WebPropertiesProvider(injector))
                .build());

        bindings.add(BindingBuilder
                .prepareBindingThat()
                .bind(Object.class)
                .annotatedWith(JndiLookup.class)
                .providedBy(new JndiProvider())
                .build());

        return bindings;
    }

}
