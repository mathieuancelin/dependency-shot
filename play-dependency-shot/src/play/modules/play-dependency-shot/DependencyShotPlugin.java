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

/**
 * This class provide injection support with Dependency-Shot for the really good
 * Play! framework.
 *
 * @author Mathieu ANCELIN
 */
public class DependencyShotPlugin extends PlayPlugin implements BeanSource {

    private List<String> modulesNames = new ArrayList<String>();
    private DSInjector injector;

    @Override
    public void onApplicationStart() {
        
        final List<Binder> modules = new ArrayList<Binder>();
        final List<Class> playClasses = Play.classloader.getAllClasses();
        Logger.info("Searching for Dependency-Shot Binders ...");
        for (final Class clazz : playClasses) {
            if (clazz.getSuperclass() != null && Binder.class.isAssignableFrom(clazz)) {
                try {
                    modules.add((Binder) clazz.newInstance());
                    modulesNames.add(clazz.getSimpleName());
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalStateException(
                            "Unable to create Dependency-Shot Binder for "
                            + clazz.getName());
                }
            }
        }
        if (modules.isEmpty()) {
            Logger.warn(
                "Couldn't find any Dependency-Shot Binders. Will work with direct bindings :(");
        } else {
            Logger.info("Found following Dependency-Shot Binders : \n"+ modules);   
        }
        this.injector = DependencyShot.getInjector(modules, null);
        play.inject.Injector.inject(this);
    }

    public <T> T getBeanOfType(Class<T> clazz) {
        if (this.injector == null) {
            Logger.warn("The Dependency-Shot injector is null. " +
                    "Maybe there is a problem with its initialization.");
            return null;
        }
        return this.injector.getInstance(clazz);
    }
}
