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
package cx.ath.mancel01.dependencyshot.configurator;

import cx.ath.mancel01.dependencyshot.api.Stage;
import cx.ath.mancel01.dependencyshot.configurator.annotations.OnStage;
import cx.ath.mancel01.dependencyshot.configurator.annotations.ProvidedBy;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorBuilder;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.ConfigurationHandler;
import cx.ath.mancel01.dependencyshot.spi.PluginsLoader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Qualifier;

/**
 * Configurator based on full annotations.
 *
 * @author Mathieu ANCELIN
 */
public class AnnotationsConfigurator extends ConfigurationHandler {

    private static final Logger logger = Logger.getLogger(AnnotationsConfigurator.class.getSimpleName());

    private String packagePrefix = "";

    private boolean showBindings = false;

    @Override
    public final InjectorImpl getInjector(Stage stage) {
        Binder binder = new AnnotationBinder();

        Collection<Class<?>> components = new ArrayList<Class<?>>();
        
        components.addAll(DSAnnotatedLoader.loadManagedBeans(packagePrefix));
        components.addAll(DSAnnotatedLoader.loadSingletons(packagePrefix));
        components.addAll(DSAnnotatedLoader.loadNamed(packagePrefix));

        for (Class<?> clazz : components) {
            String name = null;
            Named named = clazz.getAnnotation(Named.class);
            if (named != null) {
                name = named.value();
            }
            ProvidedBy providedBy = clazz.getAnnotation(ProvidedBy.class);
            Provider provider = null;
            try {
                if (providedBy != null) {
                    provider = (Provider) providedBy.value().newInstance();
                }
            } catch (Exception ex) {
                logger.log(Level.SEVERE, null, ex);
            }
            OnStage onStage = clazz.getAnnotation(OnStage.class);
            Stage stag = null;
            if (onStage != null) {
                stag = onStage.value();
            }
            Annotation[] annotations = clazz.getAnnotations();
            Class<? extends Annotation> qualifier = null;
            boolean qualifierPresent = false;
            for (Annotation anno : annotations) {
                if ((anno.annotationType().isAnnotationPresent(Qualifier.class))
                        && !(anno instanceof Named)) {
                    if (qualifierPresent) {
                        logger.log(Level.WARNING, new StringBuilder()
                                .append("Class ").append(clazz.getSimpleName())
                                .append(" has more than one qualifier.\n")
                                .append("Qualifier @")
                                .append(anno.annotationType()
                                .getSimpleName()).append(" will override @")
                                .append(qualifier.getSimpleName()).append(".")
                                .toString());
                    } else {
                        qualifierPresent = true;
                    }
                    qualifier = anno.annotationType();
                }
            }
            Class<?>[] superInterfaces = clazz.getSuperclass().getInterfaces();
            Class<?> superType = clazz.getSuperclass();
            Class<?>[] typeInterfaces = clazz.getInterfaces();
            if (typeInterfaces.length > 0) {
                for (Class interf : typeInterfaces) {
                    Binding binding = new Binding(
                            qualifier, name, interf, clazz, provider, stag);
                    binder.getBindings().put(binding, binding);
                }
            } else if (superInterfaces.length > 0) {
                Class superClass = superType;
                while(superInterfaces.length > 0) {
                    for (Class interf : superInterfaces) {
                        Binding binding = new Binding(
                                qualifier, name, interf, clazz, provider, stag);
                        binder.getBindings().put(binding, binding);
                    }
                    superClass = superClass.getSuperclass();
                    superInterfaces = superClass.getInterfaces();
                }
            } else {
                while (superType != null) {
                    if (!superType.equals(Object.class)) {
                        Binding binding = new Binding(
                                qualifier, name, superType, clazz, provider, stag);
                        binder.getBindings().put(binding, binding);
                        superType = superType.getSuperclass();
                    } else {
                        superType = null;
                    }
                }
                Binding binding = new Binding(
                        qualifier, name, clazz, clazz, provider, stag);
                binder.getBindings().put(binding, binding);
            }
        }
        if(showBindings) {
            for (Binding b : binder.getBindings().values()) {
                System.out.println(b);
            }
        }
        return InjectorBuilder.makeInjector(binder, new PluginsLoader(), stage);
    }

    @Override
    public final boolean isAutoEnabled() {
        return true;
    }

    public final AnnotationsConfigurator withPackagePrefix(String packagePrefix) {
        this.packagePrefix = packagePrefix;
        return this;
    }

    public final AnnotationsConfigurator showBindings(boolean showBindings) {
        this.showBindings = showBindings;
        return this;
    }
}
