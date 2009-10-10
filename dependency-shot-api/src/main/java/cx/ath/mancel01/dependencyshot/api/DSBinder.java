/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cx.ath.mancel01.dependencyshot.api;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import javax.inject.Provider;

/**
 * Interface for a binder.
 * 
 * @author Mathieu ANCELIN
 */
public interface DSBinder {
    /**
     * configure the binder's bindings.
     */
    void configureBindings();
    /**
     * @return the bindings of the binder.
     */
    HashMap<Class, DSBinding> getBindings();
    /**
     * Bind an interface.
     * @param generic the generic interface.
     * @return the concerned binder.
     */
    DSBinder bind(final Class generic);
    /**
     *
     * @param name
     * @return
     */
    DSBinder bind(final String name);
    /**
     *
     * @param generic
     * @return
     */
    DSBinder from(final Class generic);
    /**
     * To its implementation.
     * @param specific the implementation class.
     */
    DSBinder to(final Class specific);
    /**
     *
     * @param qualifier
     * @return
     */
    DSBinder qualifiedBy(final Class<? extends Annotation>  qualifier);
    /**
     *
     * @param <T>
     * @param provider
     * @return
     */
    <T> DSBinder providedBy(final Provider<T> provider);
    /**
     *
     * @param name
     * @return
     */
    DSBinder namedWith(final String name);


}
