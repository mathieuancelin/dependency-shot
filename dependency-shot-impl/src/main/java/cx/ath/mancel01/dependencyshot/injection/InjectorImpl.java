/*
 *  Copyright 2009 Mathieu ANCELIN.
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
package cx.ath.mancel01.dependencyshot.injection;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.exceptions.DSException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class represent an injector configured by coded
 * binders.
 * 
 * @author Mathieu ANCELIN
 */
public class InjectorImpl implements DSInjector {
    /**
     * Binders linked to the project.
     */
    private Vector<DSBinder> binders;
    /**
     * The constructor.
     */
    public InjectorImpl() {
        this.binders = new Vector();
    }
    /**
     * Return an injected instance of a binded object.
     * @param clazz the object to instanciate.
     * @return an injected object.
     */
    @Override
    public Object getObjectInstance(final Class clazz) {
        if (binders.size() > 0) {
            return AnnotationsProcessor.getInstance().processClassAnnotations(clazz, binders);
        } else {
            throw new DSException("No bindings loaded");
        }
    }
    /**
     * Return a injected object from a non injected object.
     * @param obj the object to be injected.
     * @return the injected object.
     */
    @Override
    public Object injectInstance(final Object obj) {
        if (binders.size() > 0) {
            return AnnotationsProcessor.getInstance().processInstanceAnnotations(obj, binders);
        } else {
            throw new DSException("No bindings loaded");
        }
    }
    /**
     * Inject members of an object.
     * @param obj the object to inject.
     * @return the injected object.
     */
    @Override
    public Object injectMembers(final Object obj) {
        if (binders.size() > 0) {
            return AnnotationsProcessor.getInstance().processInstanceMethodAnnotations(obj, binders);
        } else {
            throw new DSException("No bindings loaded");
        }
    }
    /**
     * Inject fields of an object.
     * @param obj the object to inject.
     * @return the injected object.
     */
    @Override
    public Object injectFields(final Object obj) {
        if (binders.size() > 0) {
            return AnnotationsProcessor.getInstance().processInstanceFieldsAnnotations(obj, binders);
        } else {
            throw new DSException("No bindings loaded");
        }
    }

    /**
     * Configure all present binders.
     */
    public void configureBinders() {
        if (binders.size() > 0) {
            for (DSBinder binder : binders) {
                binder.configureBindings();
                if (binder.getBindings().isEmpty()) {
                    Logger.getLogger(InjectorImpl.class.getName()).
                            log(Level.SEVERE, "Ooops, no bindings presents, " + "can't inject your apps ...");
                    throw new DSException("No bindings loaded");
                }
            }
        }
    }

    /**
     * Add a binder in the injector.
     * @param binder the binder to add.
     */
    public void addBinder(final DSBinder binder) {
        // TODO find multiple similar bindings and point it out
        // to the user.
        this.binders.add(binder);
    }
}
