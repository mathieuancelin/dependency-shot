package cx.ath.mancel01.dependencyshot.dsl.delegate

import cx.ath.mancel01.dependencyshot.dsl.DslBinder
import cx.ath.mancel01.dependencyshot.graph.Binding
/**
 *
 * @author mathieuancelin
 */
class BindingsDelegate {
    
    private DslBinder binder

    BindingsDelegate(DslBinder binder) {
        this.binder = binder
    }

    def methodMissing(String name, Object args) {
        if (args.length == 1) {
            if (args[0] instanceof Closure) {
                Binding binding = new Binding()
                args[0].delegate = new BindingDelegate(binding, binder)
                args[0].resolveStrategy = Closure.DELEGATE_FIRST
                args[0]()
                this.binder.bindings.put(binding, binding)
            } else {
                throw new MissingMethodException(name, this.class, args as Object[])
            }
        } else {
            throw new MissingMethodException(name, this.class, args as Object[])
        }
    }
}

