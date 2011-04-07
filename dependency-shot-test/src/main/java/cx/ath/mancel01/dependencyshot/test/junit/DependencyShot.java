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
package cx.ath.mancel01.dependencyshot.test.junit;

import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

/**
 * JUnit code runner that allow dependency injection in JUnit tests.
 *
 * @author Mathieu ANCELIN
 */
public class DependencyShot extends BlockJUnit4ClassRunner {

    private static final Logger logger = Logger.getLogger(DependencyShot.class.getSimpleName());
    private Class<?> clazz;
    private DSInjector injector;

    public DependencyShot(Class<?> clazz) throws InitializationError {
        super(clazz);
        this.clazz = clazz;
        boolean staging = false;
        Collection<Binder> binders = new ArrayList<Binder>();
        for (Method m : clazz.getDeclaredMethods()) {
            if (Modifier.isStatic(m.getModifiers()) && m.isAnnotationPresent(BinderSource.class)) {
                BinderSource source = m.getAnnotation(BinderSource.class);
                if (source.testStaging()) {
                    staging = true;
                }
                if (Collection.class.isAssignableFrom(m.getReturnType())) {
                    try {
                        binders.addAll((Collection<Binder>) m.invoke(null));
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                } else if (Binder.class.isAssignableFrom(m.getReturnType())) {
                    try {
                        binders.add((Binder) m.invoke(null));
                    } catch (Exception ex) {
                        logger.log(Level.SEVERE, null, ex);
                    }
                } else {
                    throw new RuntimeException("Your @BinderSource static method should return a Binder or a collection of binders.");
                }
            }
        }
        if (staging) {
            injector = cx.ath.mancel01.dependencyshot.DependencyShot.getInjector(
                    Stage.TEST, binders.toArray(new Binder[]{}));
        } else {
            injector = cx.ath.mancel01.dependencyshot.DependencyShot.getInjector(
                    binders.toArray(new Binder[]{}));
        }
        injector.allowCircularDependencies(true);
        System.out.println(new StringBuilder()
                .append("Running test class \"")
                .append(clazz.getSimpleName())
                .append(".java\" with Dependency-Shot container ...")
                .toString());
    }

    @Override
    protected Object createTest() throws Exception {
        return injector.getInstance(getTestClass().getJavaClass());
    }
}
