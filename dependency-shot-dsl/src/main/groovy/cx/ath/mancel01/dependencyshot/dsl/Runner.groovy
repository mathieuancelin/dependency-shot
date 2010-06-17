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

package cx.ath.mancel01.dependencyshot.dsl

import cx.ath.mancel01.dependencyshot.dsl.delegate.BindingsDelegate
import cx.ath.mancel01.dependencyshot.dsl.DslRunner
import cx.ath.mancel01.dependencyshot.graph.Binder
import cx.ath.mancel01.dependencyshot.graph.Binding

/**
 * Main Groovy class.
 *
 * @author Mathieu ANCELIN 
 */
class Runner implements DslRunner {

    ExpandoMetaClass createEMC(Class scriptClass, Closure cl) {
        ExpandoMetaClass emc = new ExpandoMetaClass(scriptClass, false)
        cl(emc)
        emc.initialize()
        return emc
    }

    Binder getBindings(String path) {
        Script dslScript = new GroovyShell().parse(new File(path).text)
        Binder binder = new DslBinder()
        dslScript.metaClass = createEMC(dslScript.class, {
            ExpandoMetaClass emc ->
            emc.bindings = {
                Closure cl ->
                cl.delegate = new BindingsDelegate(binder)
                cl.resolveStrategy = Closure.DELEGATE_FIRST
                cl()
            }
        })
        dslScript.run()
        return binder;
    }
}
