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

package cx.ath.mancel01.dependencyshot.dsl.delegate

import cx.ath.mancel01.dependencyshot.dsl.DslBinder
import cx.ath.mancel01.dependencyshot.graph.Binding
import cx.ath.mancel01.dependencyshot.api.DslConstants
/**
 *
 * @author Mathieu ANCELIN
 */
class BindingsDelegate {
    
    private DslBinder binder

    BindingsDelegate(DslBinder binder) {
        this.binder = binder
    }

    def methodMissing(String name, Object args) {
        if (args.length <= 2) {
            if (args[0] instanceof Closure && name == DslConstants.BINDING) {
                Binding binding = new Binding()
                args[0].delegate = new BindingDelegate(binding, binder)
                args[0].resolveStrategy = Closure.DELEGATE_FIRST
                args[0]()
                this.binder.bindings.put(binding, binding)
            } else {
                if (name == DslConstants.BIND) {
                    Binding binding = null
                    if (args.length == 2) {
                        binding = new Binding(args[1], args[0])
                    } else
                        binding = new Binding(args[0])
                    this.binder.bindings.put(binding, binding)
                } //else if (name == DslConstants.IMPORT) {
                //    this.binder.importBindingsFrom(args[0]);
                //}
                else {
                    throw new MissingMethodException(name, this.class, args as Object[])
                }
            }
        } else {
            throw new MissingMethodException(name, this.class, args as Object[])
        }
    }
}

