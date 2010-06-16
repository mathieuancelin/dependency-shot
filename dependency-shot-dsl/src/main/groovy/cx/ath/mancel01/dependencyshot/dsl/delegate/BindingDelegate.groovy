package cx.ath.mancel01.dependencyshot.dsl.delegate

import cx.ath.mancel01.dependencyshot.graph.Binding
import cx.ath.mancel01.dependencyshot.dsl.DslBinder

/**
 *
 * @author mathieuancelin
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
    }
    
    void setTo(Class to) {
        this.binding.to = to
    }

    void setQualifier(Class qualifier) {
        this.binding.qualifier = qualifier
    }

    void setProvider(Object provider) {
        this.binding.provider = provider
    }

    void setName(String name) {
        this.binding.name = name
    }

    void methodMissing(String name, Object args) {
        println "Method is missing : " + name
    }

    def propertyMissing(String name) {
        println "Property is missing : " + name
    }
}

