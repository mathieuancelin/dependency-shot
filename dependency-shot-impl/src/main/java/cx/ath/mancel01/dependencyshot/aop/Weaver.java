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

package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.api.DSInterceptor;
import cx.ath.mancel01.dependencyshot.exceptions.DSException;
import java.lang.reflect.Proxy;


/**
 * This class "weave" an object with its interceptors.
 * 
 * @author Mathieu ANCELIN
 */
public final class Weaver {
    /**
     * The unique instance of the class.
     **/
    private static Weaver instance = null;

    /**
     * The private constructor of the singleton.
     **/
    private Weaver() {

    }
    /**
     * The accessor for the unique instance of the singleton.
     * @return the unique instance.
     */
    public static synchronized Weaver getInstance() {
        if (instance == null) {
            instance = new Weaver();
        }
        return instance;
    }
    /**
     * Create a proxy on an object that handle calls on this object
     * and execute its interceptors chain.
     * 
     * @param <T> the type of the interface.
     * @param iface the interface class.
     * @param o the concerned object.
     * @param interceptors the interceptors chain.
     * @return a proxy object that handle calls on the concerned object.
     */
    public <T> T weaveObject(final Class<T> iface, final Object o, final DSInterceptor[] interceptors) {
        try {
            UserInvocationHandler handler = new UserInvocationHandler(o, interceptors);
            return (T) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{iface}, handler);
        } catch (Exception e) {
            throw new DSException(e.getMessage());
        }
    }
}
