/*
 *  Copyright 2009-2010 Mathieu ANCELIN
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
import cx.ath.mancel01.dependencyshot.api.DSInvocation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * The handler needed for proxy in case of using aop interceptors.
 * 
 * @author Mathieu ANCELIN
 */
public class UserInvocationHandler implements InvocationHandler {
    /**
     * The concerned object.
     */
    private Object bean;
    /**
     * The chain of interceptors.
     */
    private DSInterceptor[] interceptors;
    /**
     * Constructor.
     * @param bean The concerned object.
     * @param interceptors The chain of interceptors.
     */
    public UserInvocationHandler(final Object bean, DSInterceptor[] interceptors) {
        this.bean = bean;
        this.interceptors = interceptors;
    }
    /**
     * Handle the invocations on the interceptors chain.
     * @param proxy the proxy for handling calls.
     * @param method the targeted methods.
     * @param args the args of the method.
     * @return the result of the invocation.
     * @throws Throwable exceptions launched by the interceptors chain.
     */
    @Override
    public final Object invoke(final Object proxy, final Method method, Object[] args) throws Throwable {
        DSInvocation invocation = new DSInvocation(bean, interceptors, method, args);
        return invocation.nextInterceptor();
    }
}
