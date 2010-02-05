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
    /**
     * @return if a binder contains no binding.
     */
    boolean isEmpty();
    /**
     * Set the injector of a binder.
     *
     * @param injector the injector of the binder.
     */
    void setInjector(DSInjector injector);
    /**
     * Configuration of the last binding (workaround for fluent chained binding API)
     */
    void configureLastBinding();
}
