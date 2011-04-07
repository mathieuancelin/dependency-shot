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
import cx.ath.mancel01.dependencyshot.api.event.EventListener;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.scope.Dependent;
import cx.ath.mancel01.dependencyshot.scope.DependentScope;
import cx.ath.mancel01.dependencyshot.scope.SingletonScope;
import cx.ath.mancel01.dependencyshot.scope.ThreadScope;
import cx.ath.mancel01.dependencyshot.scope.ThreadScoped;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import javax.inject.Singleton;

/**
 * SPI plugins mamanger.
 * 
 * @author Mathieu ANCELIN
 */
public final class PluginsLoader {
    /**
     * Logger.
     */
    private static final Logger logger = Logger.getLogger(PluginsLoader.class.getSimpleName());
    /**
     * Handled SPI plugins.
     */
    private Collection<Binding> providedBindings;
    /**
     * Handled SPI plugins.
     */
    private Collection<ImplementationValidator> validators;
    /**
     * Handled SPI plugins.
     */
    private Collection<InstanceHandler> instanceHandlers;
    /**
     * Handled SPI plugins.
     */
    private Collection<InstanceLifecycleHandler> lifecycleHandlers;
    /**
     * Handled SPI plugins.
     */
    private Collection<ConfigurationHandler> configurationHandlers;
    /**
     * Listeners SPI plugins.
     */
    private Collection<EventListener> eventListeners;
    /**
     * Handled SPI plugins.
     */
    private Map<Class<? extends Annotation>, CustomScopeHandler> scopeHandlers;
    /**
     * Handled SPI plugins.
     */
    private Collection<Reflector> reflectors;

    /**
     * The private constructor of the singleton.
     */
    public PluginsLoader() {
    }

    /**
     * Load all the plugins.
     *
     * @param injector the actual injector.
     */
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
        scopeHandlers = loadCustomScopeHandlers(injector);
        sb.append("CustomScopeHandler plugins loaded : ");
        sb.append(scopeHandlers.size());
        sb.append("\n");
        eventListeners = loadEventListeners();
        sb.append("Event listeners plugins loaded : ");
        sb.append(eventListeners.size());
        sb.append("\n");
        if (DependencyShot.DEBUG) {
            logger.info(sb.toString());
        }
    }
    /**
     * Load the plugins at the very beginning of the bootstrap.
     */
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
    /**
     * Load binding provider bindings.
     * 
     * @param injector actual injector
     * @return provided bindings.
     */
    private Collection<Binding> loadProvidedBindings(InjectorImpl injector) {
        ArrayList<Binding> bindings = new ArrayList<Binding>();
        //ServiceLoader<BindingsProvider> providersLoader = ServiceLoader.load(BindingsProvider.class);
        DSServiceLoader<BindingsProvider> providersLoader = DSServiceLoader.load(BindingsProvider.class);
        //providersLoader.reload();
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
    /**
     * Load instance handlers.
     * 
     * @return loade instance handlers.
     */
    private Collection<InstanceHandler> loadInstanceHandlers() {
        ArrayList<InstanceHandler> handlers = new ArrayList<InstanceHandler>();
        //ServiceLoader<InstanceHandler> handlersProvider = ServiceLoader.load(InstanceHandler.class);
        DSServiceLoader<InstanceHandler> handlersProvider = DSServiceLoader.load(InstanceHandler.class);
        //handlersProvider.reload();
        Iterator<InstanceHandler> handlersIterator = handlersProvider.iterator();
        while (handlersIterator.hasNext()) {
            InstanceHandler handler = handlersIterator.next();
            handlers.add(handler);
        }
        return handlers;
    }
    /**
     * Load lifecycle handlers.
     *
     * @return loaded lifecycle handlers.
     */
    private Collection<InstanceLifecycleHandler> loadLifecycleHandlers() {
        ArrayList<InstanceLifecycleHandler> handlers = new ArrayList<InstanceLifecycleHandler>();
        //ServiceLoader<InstanceLifecycleHandler> handlersProvider = ServiceLoader.load(InstanceLifecycleHandler.class);
        DSServiceLoader<InstanceLifecycleHandler> handlersProvider = DSServiceLoader.load(InstanceLifecycleHandler.class);
        //handlersProvider.reload();
        Iterator<InstanceLifecycleHandler> handlersIterator = handlersProvider.iterator();
        while (handlersIterator.hasNext()) {
            InstanceLifecycleHandler handler = handlersIterator.next();
            handlers.add(handler);
        }
        return handlers;
    }
    /**
     * Load validators.
     *
     * @return loaded validators.
     */
    private Collection<ImplementationValidator> loadImplementationValidators() {
        ArrayList<ImplementationValidator> implemvalidators = new ArrayList<ImplementationValidator>();
        //ServiceLoader<ImplementationValidator> validatorsProvider = ServiceLoader.load(ImplementationValidator.class);
        DSServiceLoader<ImplementationValidator> validatorsProvider = DSServiceLoader.load(ImplementationValidator.class);
        //validatorsProvider.reload();
        Iterator<ImplementationValidator> validatorsIterator = validatorsProvider.iterator();
        while (validatorsIterator.hasNext()) {
            ImplementationValidator validator = validatorsIterator.next();
            implemvalidators.add(validator);
        }
        return implemvalidators;
    }
    /**
     * Load configurator.
     *
     * @return loaded configurators.
     */
    private Collection<ConfigurationHandler> loadConfigurationHandlers() {
        ArrayList<ConfigurationHandler> configHandlers = new ArrayList<ConfigurationHandler>();
        //ServiceLoader<ConfigurationHandler> configHandlersProvider = ServiceLoader.load(ConfigurationHandler.class);
        DSServiceLoader<ConfigurationHandler> configHandlersProvider = DSServiceLoader.load(ConfigurationHandler.class);
        //configHandlersProvider.reload();
        Iterator<ConfigurationHandler> validatorsIterator = configHandlersProvider.iterator();
        while (validatorsIterator.hasNext()) {
            ConfigurationHandler validator = validatorsIterator.next();
            configHandlers.add(validator);
        }
        return configHandlers;
    }

    private Map<Class<? extends Annotation>, CustomScopeHandler> loadCustomScopeHandlers(InjectorImpl injector) {
        HashMap<Class<? extends Annotation>, CustomScopeHandler> handlers =
                new HashMap<Class<? extends Annotation>, CustomScopeHandler>();
        DSServiceLoader<CustomScopeHandler> scopeHandlersProvider = DSServiceLoader.load(CustomScopeHandler.class);
        //scopeHandlersProvider.reload();
        Iterator<CustomScopeHandler> handlersIterator = scopeHandlersProvider.iterator();
        while (handlersIterator.hasNext()) {
            CustomScopeHandler handler = handlersIterator.next();
            handlers.put(handler.getScope(), handler);
        }
        handlers.put(Dependent.class, new DependentScope());
        handlers.put(Singleton.class, new SingletonScope());
        handlers.put(ThreadScoped.class, new ThreadScope());
        return handlers;
    }

    /**
     * Load eventListeners.
     *
     * @return loaded eventListeners.
     */
    private Collection<EventListener> loadEventListeners() {
        ArrayList<EventListener> listeners = new ArrayList<EventListener>();
        //ServiceLoader<ConfigurationHandler> configHandlersProvider = ServiceLoader.load(ConfigurationHandler.class);
        DSServiceLoader<EventListener> listenersProvider = DSServiceLoader.load(EventListener.class);
        //configHandlersProvider.reload();
        Iterator<EventListener> validatorsIterator = listenersProvider.iterator();
        while (validatorsIterator.hasNext()) {
            EventListener validator = validatorsIterator.next();
            listeners.add(validator);
        }
        return listeners;
    }
    
    /**
     * Load reflector services.
     *
     * @return loaded reflector services.
     */
    private Collection<Reflector> loadReflectors() {
        ArrayList<Reflector> loadedReflectors = new ArrayList<Reflector>();
        return loadedReflectors;
    }
    /**
     * @return provided bindings.
     */
    public final Collection<Binding> getProvidedBindings() {
        return providedBindings;
    }
    /**
     * @return instance handlers.
     */
    public Collection<InstanceHandler> getInstanceHandlers() {
        return instanceHandlers;
    }
    /**
     * @return lifecycle handlers.
     */
    public Collection<InstanceLifecycleHandler> getLifecycleHandlers() {
        return lifecycleHandlers;
    }
    /**
     * @return validators.
     */
    public Collection<ImplementationValidator> getValidators() {
        return validators;
    }
    /**
     * @return reflector services.
     */
    public Collection<Reflector> getReflectors() {
        return reflectors;
    }
    /**
     * @return configurators.
     */
    public Collection<ConfigurationHandler> getConfigurationHandlers() {
        return configurationHandlers;
    }

    public Map<Class<? extends Annotation>, CustomScopeHandler> getScopeHandlers() {
        return scopeHandlers;
    }

    public Collection<EventListener> getEventListeners() {
        return eventListeners;
    }
    
}
