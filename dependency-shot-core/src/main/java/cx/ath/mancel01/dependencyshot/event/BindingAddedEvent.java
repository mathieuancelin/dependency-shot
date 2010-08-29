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

package cx.ath.mancel01.dependencyshot.event;

import cx.ath.mancel01.dependencyshot.api.event.Event;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.graph.Binding;

/**
 *
 * @author Mathieu ANCELIN
 */
public class BindingAddedEvent extends Event {

    private DSInjector injector;

    private Binding binding;

    private Binder binder;

    public BindingAddedEvent(DSInjector injector, Binding binding, Binder binder) {
        this.injector = injector;
        this.binding = binding;
        this.binder = binder;
    }

    public DSInjector getInjector() {
        return injector;
    }

    public Binder getBinder() {
        return binder;
    }

    public Binding getBinding() {
        return binding;
    }
    
}
