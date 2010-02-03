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
import cx.ath.mancel01.dependencyshot.exceptions.InvocationException;
import java.lang.reflect.Method;

/**
 * This class define an final interceptor.
 * This interceptor is the last interceptor invoked and
 * handle the real invocation of the called method.
 * 
 * @author Mathieu ANCELIN
 */
public class FinalInterceptor implements DSInterceptor {
    /**
     * Invoke the right method on the intercepted object.
     * @param invocation the invocation on intercepted method.
     * @return the return of the invocation.
     */
    @Override
    public final Object invoke(final DSInvocation invocation) {
        try {
            Object bean = invocation.getBean();
            Method method = invocation.getMethod();
            Object[] args = invocation.getArgs();
            return method.invoke(bean, args);
        } catch (Exception e) {
            throw new InvocationException(e);
        }
    }
}
