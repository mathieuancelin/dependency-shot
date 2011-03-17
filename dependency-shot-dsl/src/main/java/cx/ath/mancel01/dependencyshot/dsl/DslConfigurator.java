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

package cx.ath.mancel01.dependencyshot.dsl;

import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.injection.InjectorBuilder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.ConfigurationHandler;
import cx.ath.mancel01.dependencyshot.spi.PluginsLoader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * Configurator for binders written in groovy.
 *
 * @author Mathieu ANCELIN
 */
public class DslConfigurator extends ConfigurationHandler {

    private static final Logger logger = Logger.getLogger(DslConfigurator.class.getSimpleName());

    private List<String> binderFiles = new ArrayList<String>();

    public DslConfigurator binder(String path) {
        binderFiles.add(path);
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public InjectorImpl getInjector(Stage stage) {
        Collection<Binder> binders = new ArrayList<Binder>();
        for (String path : binderFiles) {
            DslRunner runner = new cx.ath.mancel01.dependencyshot.dsl.Runner();
            binders.add(runner.getBindings(path));
        }
        return InjectorBuilder.makeInjector(binders, new PluginsLoader(), stage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Object getDelegate() {
        return this; 
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAutoEnabled() {
        return false;
    }
}
