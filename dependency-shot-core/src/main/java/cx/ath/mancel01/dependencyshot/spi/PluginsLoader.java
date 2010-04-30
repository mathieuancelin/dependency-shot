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

package cx.ath.mancel01.dependencyshot.spi;

import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.ServiceLoader;

/**
 *
 * @author Mathieu ANCELIN
 */
public final class PluginsLoader {

    /**
     * The unique instance of the class.
     */
    private static PluginsLoader instance = null;

    private Collection<Binding> providedBindings;

    /**
     * The private constructor of the singleton.
     */
    private PluginsLoader() {

    }

    /**
     * The accessor for the unique instance of the singleton.
     * @return the unique instance of the singleton.
     */
    public static synchronized PluginsLoader getInstance() {
        if (instance == null) {
            instance = new PluginsLoader();
        }
        return instance;
    }

    public final void reload(InjectorImpl injector) {
        providedBindings = getProvidedBindings(injector);
    }

    private Collection<Binding> getProvidedBindings(InjectorImpl injector) {
        ArrayList<Binding> bindings = new ArrayList<Binding>();
        ServiceLoader<BindingsProvider> providersLoader = ServiceLoader.load(BindingsProvider.class);
        providersLoader.reload();
        Iterator<BindingsProvider> providersIterator = providersLoader.iterator();
		while (providersIterator.hasNext()) {
			BindingsProvider provider = providersIterator.next();
            Collection<Binding> bindingsProvided = provider.getProvidedBindings(injector);
            for (Binding binding : bindingsProvided) {
                bindings.add(binding);
            }
		}
        return bindings;
    }

    public final Collection<Binding> getProvidedBindings() {
        return providedBindings;
    }

    public final void setProvidedBindings(Collection<Binding> providedBindings) {
        this.providedBindings = providedBindings;
    }
}
