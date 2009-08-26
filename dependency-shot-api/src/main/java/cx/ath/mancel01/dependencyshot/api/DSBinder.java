/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package cx.ath.mancel01.dependencyshot.api;

import java.util.HashMap;

/**
 *
 * @author Mathieu ANCELIN
 */
public interface DSBinder {

    void configureBindings();
    
    HashMap<Class, DSBinding> getBindings();

    DSBinder bind(Class generic);

    void to(Class specific);
}
