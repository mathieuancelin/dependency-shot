/*
 *  Copyright 2009 Mathieu ANCELIN.
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
package cx.ath.mancel01.dependencyshot.graph;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.DSBinding;
import java.util.Vector;

/**
 * Utility class on objects graph (binders/bindings graph).
 * @author Mathieu ANCELIN
 */
public final class GraphHelper {

    /**
     * The unique instance of the class.
     */
    private static GraphHelper instance = null;

    /**
     * The private constructor of the singleton.
     */
    private GraphHelper() {
    }

    /**
     * The accessor for the unique instance of the singleton.
     * @return the unique instance.
     */
    public static synchronized GraphHelper getInstance() {
        if (instance == null) {
            instance = new GraphHelper();
        }
        return instance;
    }
    /**
     * Find if a matching binding is present.
     * @param clazz the class to match.
     * @param binders in the binders.
     * @return the right binding or null.
     */
    public DSBinding findBinding(final Class clazz, final Vector<DSBinder> binders) {
        for (DSBinder binder : binders) {
            for (DSBinding binding : binder.getBindings().values()) {
                if (binding.getGeneric().equals(clazz)) {
                    return binding;
                }
            }
        }
        return null;
    }
}
