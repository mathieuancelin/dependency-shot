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

import cx.ath.mancel01.dependencyshot.api.annotations.InjectLogger;
import cx.ath.mancel01.dependencyshot.api.annotations.Property;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.BindingsProvider;
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
        bindings.add(new Binding(InjectLogger.class, null, Logger.class,
                Logger.class, new LoggerProvider(), null));
        bindings.add(new Binding(Property.class, null, String.class,
                String.class, new PropertiesProvider(injector), null));
        return bindings;
    }

}
