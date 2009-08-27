/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cx.ath.mancel01.dependencyshot.api;

import java.util.HashMap;

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
     * To its implementation.
     * @param specific the implementation class.
     */
    void to(final Class specific);
}
