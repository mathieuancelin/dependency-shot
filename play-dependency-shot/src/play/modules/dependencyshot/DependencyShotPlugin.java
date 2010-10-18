/*
 *  Copyright 2009-2010 Mathieu ANCELIN
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

package play.modules.dependencyshot;

import java.util.ArrayList;
import java.util.List;
import play.Play;
import play.PlayPlugin;
import play.inject.BeanSource;
import play.Logger;
import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import java.util.HashMap;
import java.util.Map;
import play.jobs.Job;
import play.mvc.Controller;
import play.mvc.Mailer;

/**
 * This class provide injection support with Dependency-Shot for the really good
 * Play! framework.
 *
 * @author Mathieu ANCELIN
 */
public class DependencyShotPlugin extends PlayPlugin implements BeanSource {

    private DSInjector injector;

    @Override
    public void onApplicationStart() {
        
        final List<String> modulesNames = new ArrayList<String>();
        final Map<Class, Binder> modules = new HashMap<Class, Binder>();
        final List<Class> playClasses = Play.classloader.getAssignableClasses(Binder.class);
        Logger.info("Searching for Dependency-Shot Binders ...");
        for (final Class clazz : playClasses) {
            try {
                if (!modules.containsKey(clazz)) {
                    Binder binder = (Binder) clazz.newInstance();
                    modules.put(clazz, binder);
                    modulesNames.add(clazz.getSimpleName());
                }
            } catch (Exception e) {
                throw new IllegalStateException(
                        "Unable to create Dependency-Shot Binder for "
                        + clazz.getName(), e);
            }
        }
        injector = null;
        if (modules.isEmpty()) {
            findAlternateConfigurationHandlers();
            if (modules.isEmpty() && injector == null) {
                Logger.warn(
                    "Couldn't find any Dependency-Shot Binders. Will work with direct bindings :(");
            }
        } else {
            Logger.info("Found following Dependency-Shot Binders : ");
            for (String name : modulesNames) {
                Logger.info("    " + name);
            }
        }
        if (injector == null) {
            injector = DependencyShot.getInjector(modules.values(), null);
        }
        if (injector != null) {
            injector.allowCircularDependencies(true);
            injector.registerShutdownHook();
            InjectorLocator.getInstance().setInjector(injector);
            //play.inject.Injector.inject(this);
            injectPlayClasses();
        } else {
            Logger.error("Error while starting injector. Can't inject Play! objects :(");
        }
    }

    /**
     * Method that replace Play! {@code play.inject.Injector.inject(this);} to
     * support full JSR-330 injection points.
     */
    private void injectPlayClasses() {
        List<Class> classes = Play.classloader.getAssignableClasses(Controller.class);
        classes.addAll(Play.classloader.getAssignableClasses(Mailer.class));
        classes.addAll(Play.classloader.getAssignableClasses(Job.class));
        for(Class<?> clazz : classes) {
            // injection statique
            injector.injectStatics(clazz);
        }
    }

    /**
     * search for alternative configuration of the injector.
     */
    private void findAlternateConfigurationHandlers() {
        // check annotations configuration
        try {
            Class.forName("cx.ath.mancel01.dependencyshot.configurator.AnnotationsConfigurator");
            injector = DependencyShot.getSpecificConfigurator().getInjector();
            Logger.info("Injection will be performed through annotations bindings :)");
            return;
        } catch (ClassNotFoundException ex) {
            // silently ignore this exception
        }
        // check for groovy DSL
        try {
            Class.forName("cx.ath.mancel01.dependencyshot.dsl.DslConfigurator");
            String groovyFilePath = Play.configuration
                    .getProperty("groovy.bindings.file", "bindings.groovy");
            injector = DependencyShot.getSpecificConfigurator()
                    .getInjector(groovyFilePath);
            Logger.info("Injection will be performed through groovy bindings :)");
            return;
        } catch (ClassNotFoundException ex) {
            // silently ignore this exception
        }
    }

    @Override
    public <T> T getBeanOfType(Class<T> clazz) {
        if (this.injector == null) {
            Logger.warn("The Dependency-Shot injector is null. " +
                    "Maybe there is a problem with its initialization.");
            return null;
        }
        return this.injector.getInstance(clazz);
    }
}
