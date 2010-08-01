/*
 *  Copyright 2009-2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.dynamic;

import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.util.Observable;

/**
 * A service with a dynamic implementation.
 *
 * @author Mathieu ANCELIN
 */
public class DynamicService extends Observable {
    
    private Binding binding;
    
    private Object actualImplementationInstance;

    private InjectorImpl injector;

    public DynamicService(Binding binding, Object instance, InjectorImpl injector) {
        this.binding = binding;
        this.actualImplementationInstance = instance;
        this.injector = injector;
    }

    public final Binding getBinding() {
        return binding;
    }

    public final Class getImplementation() {
        return binding.getTo();
    }

    public final Class getInterface() {
        return binding.getFrom();
    }

    public final Object getActualInstance() {
        return actualImplementationInstance;
    }

    public final void changeImpl(Class newImpl) {
        this.binding.setTo(newImpl);
        this.injector.setBindingsChanged(true);
        Object instance = binding.getInstance(injector, null); // TODO handle injection points
        Event event = new Event(this);
        event.setValue(instance);
        this.setChanged();
        this.notifyObservers(event);
    }
}
