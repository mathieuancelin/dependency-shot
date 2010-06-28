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

import cx.ath.mancel01.dependencyshot.graph.Binding
import cx.ath.mancel01.dependencyshot.dsl.DslBinder
import cx.ath.mancel01.dependencyshot.api.Stage
import cx.ath.mancel01.dependencyshot.injection.util.InstanceProvider
import javax.inject.Provider

/**
 *
 * @author Mathieu ANCELIN
 */
class BindingDelegate {

    private Binding binding

    private DslBinder binder

    BindingDelegate(Binding binding, DslBinder binder) {
        this.binding = binding
        this.binder = binder
    }

    void setFrom(Class from) {
        this.binding.from = from
        this.binding.to = from
    }
    
    void setTo(Class to) {
        this.binding.to = to
    }

    void setAnnotedWith(Class qualifier) {
        this.binding.qualifier = qualifier
    }

    void setProvidedBy(Provider provider) {
        this.binding.provider = provider
    }

    void setNamed(String name) {
        this.binding.name = name
    }

    void setOnStage(Stage stage) {
        this.binding.stage = stage
    }

    void setToInstance(Object instance) {
        this.binding.provider = new InstanceProvider(instance)
    }

    def methodMissing(String name, Object args) {
        println "Method is missing : " + name
    }

    def propertyMissing(String name) {
        println "Property is missing : " + name
    }
}

