/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cx.ath.mancel01.dependencyshot.api;

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
    
    boolean isEmpty();

    void setInjector(DSInjector injector);

    void configureLastBinding();

}
