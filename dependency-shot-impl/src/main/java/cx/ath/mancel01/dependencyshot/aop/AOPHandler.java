/*
 *  Copyright 2009 mathieuancelin.
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
 *
 * @author mathieuancelin
 */
public class AOPHandler {

    /**
     * The unique instance of the class
     **/
    private static AOPHandler INSTANCE = null;

    private DSInterceptor defaultInterceptor;

    /**
     * The private constructor of the singleton
     **/
    private AOPHandler() { 

    }

    /**
     * The accessor for the unique instance of the singleton
     **/
    public static synchronized AOPHandler getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new AOPHandler();
        }
        return INSTANCE;
    }

    public <T> T weaveObject(Class<T> iface, Object o, DSInterceptor[] interceptors) {
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
