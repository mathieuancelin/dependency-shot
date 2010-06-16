package cx.ath.mancel01.dependencyshot.dsl

import cx.ath.mancel01.dependencyshot.dsl.delegate.BindingsDelegate
import cx.ath.mancel01.dependencyshot.dsl.DslRunner
import cx.ath.mancel01.dependencyshot.graph.Binder
import cx.ath.mancel01.dependencyshot.graph.Binding

/**
 * Main Groovy class.
 *
 * @author mathieuancelin 
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
