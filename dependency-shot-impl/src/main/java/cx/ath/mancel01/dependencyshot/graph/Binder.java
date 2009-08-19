/*
 *  Copyright 2009 mathieuancelin.
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

package cx.ath.mancel01.dependencyshot.graph;

import cx.ath.mancel01.dependencyshot.api.IBinder;
import cx.ath.mancel01.dependencyshot.api.IBinding;
import java.util.HashMap;

/**
 *
 * @author mathieuancelin
 */
public abstract class Binder implements IBinder {

    private HashMap<Class, IBinding> bindings;

    public Binder(){
        bindings = new HashMap<Class, IBinding>();
    }

    public abstract void configureBindings();

    public void bind(Class generic, Class specific){
        Binding binding = new Binding();
        binding.setGeneric(generic);
        binding.setSpecific(specific);
        this.bindings.put(generic, binding);
    }

    private void unbind(Class generic, Class specific){
        //this.bindings.remove(generic);
    }

    public HashMap<Class, IBinding> getBindings() {
        return bindings;
    }

    public void setBindings(HashMap<Class, IBinding> bindings) {
        this.bindings = bindings;
    }

}
