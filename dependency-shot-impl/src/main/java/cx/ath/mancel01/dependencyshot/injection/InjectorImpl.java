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
 *
 * @author Mathieu ANCELIN
 */
public class InjectorImpl implements DSInjector {

    private Vector<DSBinder> binders;

    public InjectorImpl() {
        this.binders = new Vector();
    }

    @Override
    public Object getObjectInstance(Class clazz) {
        return AnnotationsProcessor.getInstance().processClassAnnotations(clazz, binders);
    }

    @Override
    public Object injectInstance(Object obj) {
        return AnnotationsProcessor.getInstance().processInstanceAnnotations(obj, binders);
    }

    @Override
    public Object injectMembers(Object obj) {
        return AnnotationsProcessor.getInstance().processInstanceMethodAnnotations(obj, binders);
    }

    @Override
    public Object injectFiels(Object obj) {
        return AnnotationsProcessor.getInstance().processInstanceFieldsAnnotations(obj, binders);
    }

    public void configureBinders() {
        if (binders.size() > 0) {
            for (DSBinder binder : binders) {
                if (!binder.getBindings().isEmpty()) {
                    binder.configureBindings();
                } else {
                    Logger.getLogger(InjectorImpl.class.getName()).
                            log(Level.SEVERE, "Ooops, no bindings presents, " 
                            + "can't inject your apps ...");
                    throw new DSException("no bindings");
                }
            }
        }
    }

    public void addBinder(DSBinder binder) {
        this.binders.add(binder);
    }
}
