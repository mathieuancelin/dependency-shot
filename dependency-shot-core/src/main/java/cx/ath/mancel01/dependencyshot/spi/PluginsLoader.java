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
package cx.ath.mancel01.dependencyshot.spi;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.logging.Logger;

/**
 *
 * @author Mathieu ANCELIN
 */
public final class PluginsLoader {

    private static Logger logger = Logger.getLogger(PluginsLoader.class.getName());
    /**
     * The unique instance of the class.
     */
    private static PluginsLoader instance = null;
    private Collection<Binding> providedBindings;
    private Collection<ImplementationValidator> validators;
    private Collection<InstanceHandler> instanceHandlers;
    private Collection<InstanceLifecycleHandler> lifecycleHandlers;
    private Collection<ConfigurationHandler> configurationHandlers;
    private Collection<Reflector> reflectors;

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

    public final void loadPlugins(InjectorImpl injector) {
        loadFirstPlugins();
        StringBuilder sb = new StringBuilder();
        sb.append("\n====== Plugins (level 2) management =======\n\n");
        providedBindings = loadProvidedBindings(injector);
        sb.append("Provided bindings loaded : ");
        sb.append(providedBindings.size());
        sb.append("\n");
        validators = loadImplementationValidators();
        sb.append("Validator plugins loaded : ");
        sb.append(validators.size());
        sb.append("\n");
        instanceHandlers = loadInstanceHandlers();
        sb.append("InstanceHandler plugins loaded : ");
        sb.append(instanceHandlers.size());
        sb.append("\n");
        lifecycleHandlers = loadLifecycleHandlers();
        sb.append("LifecycleHandler plugins loaded : ");
        sb.append(lifecycleHandlers.size());
        sb.append("\n");
        if (DependencyShot.DEBUG) {
            logger.info(sb.toString());
        }
    }

    public final void loadFirstPlugins() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n====== Plugins (level 1) management =======\n\n");
        configurationHandlers = loadConfigurationHandlers();
        sb.append("ConfigurationHandler plugins loaded : ");
        sb.append(configurationHandlers.size());
        sb.append("\n");
        reflectors = loadReflectors();
        sb.append("Reflector plugins loaded : ");
        sb.append(reflectors.size());
        sb.append("\n\n==================================\n");
        if (DependencyShot.DEBUG) {
            logger.info(sb.toString());
        }
    }

    private Collection<Binding> loadProvidedBindings(InjectorImpl injector) {
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

    private Collection<InstanceHandler> loadInstanceHandlers() {
        ArrayList<InstanceHandler> handlers = new ArrayList<InstanceHandler>();
        ServiceLoader<InstanceHandler> handlersProvider = ServiceLoader.load(InstanceHandler.class);
        handlersProvider.reload();
        Iterator<InstanceHandler> handlersIterator = handlersProvider.iterator();
        while (handlersIterator.hasNext()) {
            InstanceHandler handler = handlersIterator.next();
            handlers.add(handler);
        }
        return handlers;
    }

    private Collection<InstanceLifecycleHandler> loadLifecycleHandlers() {
        ArrayList<InstanceLifecycleHandler> handlers = new ArrayList<InstanceLifecycleHandler>();
        ServiceLoader<InstanceLifecycleHandler> handlersProvider = ServiceLoader.load(InstanceLifecycleHandler.class);
        handlersProvider.reload();
        Iterator<InstanceLifecycleHandler> handlersIterator = handlersProvider.iterator();
        while (handlersIterator.hasNext()) {
            InstanceLifecycleHandler handler = handlersIterator.next();
            handlers.add(handler);
        }
        return handlers;
    }

    private Collection<ImplementationValidator> loadImplementationValidators() {
        ArrayList<ImplementationValidator> implemvalidators = new ArrayList<ImplementationValidator>();
        ServiceLoader<ImplementationValidator> validatorsProvider = ServiceLoader.load(ImplementationValidator.class);
        validatorsProvider.reload();
        Iterator<ImplementationValidator> validatorsIterator = validatorsProvider.iterator();
        while (validatorsIterator.hasNext()) {
            ImplementationValidator validator = validatorsIterator.next();
            implemvalidators.add(validator);
        }
        return implemvalidators;
    }

    private Collection<ConfigurationHandler> loadConfigurationHandlers() {
        ArrayList<ConfigurationHandler> configHandlers = new ArrayList<ConfigurationHandler>();
        ServiceLoader<ConfigurationHandler> configHandlersProvider = ServiceLoader.load(ConfigurationHandler.class);
        configHandlersProvider.reload();
        Iterator<ConfigurationHandler> validatorsIterator = configHandlersProvider.iterator();
        while (validatorsIterator.hasNext()) {
            ConfigurationHandler validator = validatorsIterator.next();
            configHandlers.add(validator);
        }
        return configHandlers;
    }

    private Collection<Reflector> loadReflectors() {
        ArrayList<Reflector> loadedReflectors = new ArrayList<Reflector>();
        return loadedReflectors;
    }

    public final Collection<Binding> getProvidedBindings() {
        return providedBindings;
    }

    public Collection<InstanceHandler> getInstanceHandlers() {
        return instanceHandlers;
    }

    public Collection<InstanceLifecycleHandler> getLifecycleHandlers() {
        return lifecycleHandlers;
    }

    public Collection<ImplementationValidator> getValidators() {
        return validators;
    }

    public Collection<Reflector> getReflectors() {
        return reflectors;
    }

    public Collection<ConfigurationHandler> getConfigurationHandlers() {
        return configurationHandlers;
    }
}
