/*
 *  Copyright 2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.dynamic.config;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.dynamic.scope.DynamicImplementation;
import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.graph.BinderAccessor;
import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieu
 */
public abstract class DynamicBinder extends Binder {

    private static final Logger logger = Logger.getLogger(DynamicBinder.class.getName());

    private List<DSBinder> delegateBinders = new ArrayList<DSBinder>();

    private boolean isOSGi = false;

    public DynamicBinder() {
    }

    public DynamicBinder(DSBinder... binders) {
        delegateBinders.addAll(Arrays.asList(binders));
    }

    @Override
    public final void configureBindings() {
        for (DSBinder binder: delegateBinders) {
            BinderAccessor.setInjector((Binder) binder, (InjectorImpl) injector());
            binder.configureBindings();
            BinderAccessor.configureLastBinding((Binder) binder);
        }
        configure();
    }

    public abstract void configure();

    public void bindDynamically(Class<?> from) {
        try {
            bind(from);
            Field fTo = Binder.class.getDeclaredField("to");
            fTo.setAccessible(true);
            fTo.set(this, DynamicImplementation.class);
            fTo.setAccessible(false);   
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unable to bind dynamically the class " + from.getName(), ex);
        }
        validate(from);
    }

    private void validate(Class<?> dynamic) {
        ArrayList<Class<?>> errors = new ArrayList<Class<?>>();
        Collection<Binding<?>> registered = this.getBindings().keySet();
        for (Binding binding : registered) {
            if (binding.getFrom() != null && binding.getTo() != null 
                    && dynamic.equals(binding.getFrom())
                    && !binding.getTo().equals(DynamicImplementation.class)) {
                errors.add(binding.getFrom());
            }
        }
        if (!errors.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("The following classes are both declared for dynamic injection and static injection.");
            builder.append("\nIt can't append !\n");
            for (Class<?> clazz : errors) {
                builder.append("  ");
                builder.append(clazz.getName());
                builder.append("\n");
            }
            throw new DSIllegalStateException(builder.toString());
        }
    }
}
