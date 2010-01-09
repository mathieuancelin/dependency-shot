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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is an internal user interceptor. It define a method to call before
 * and after an intercepted method. It matches a method with @AroundInvoke
 * annotation present in an @Interceptors value class.
 * 
 * @author Mathieu ANCELIN
 */
public class UserInterceptor implements DSInterceptor {

    /**
     * The intercepted method.
     */
    private Method interceptMethod = null;
    /**
     * The method to call before and after intercepted method.
     */
    private Method annotedMethod = null;
    /**
     * The object where annotedMethod is defined.
     */
    private Object interceptedObject = null;
    /**
     * Constructor.
     * @param method the targeted method.
     * @param object from this object.
     */
    public UserInterceptor(final Method method, final Object object) {
        this.interceptMethod = method;
        this.interceptedObject = object;
    }
    /**
     * Set the annoted (intercept) method.
     * @param annotedMethod the annoted method.
     */
    public void setAnnotedMethod(final Method annotedMethod) {
        this.annotedMethod = annotedMethod;
    }
    /**
     * Invoke the next interceptor in the interceptor chain.
     * @param invocation the invocation.
     * @return the return of the invocation.
     */
    @Override
    public Object invoke(final DSInvocation invocation) {
        try {
            if (annotedMethod != null) {
                if (invocation.getMethod().getName().equals(annotedMethod.getName())) {
                    return this.interceptMethod.invoke(interceptedObject, invocation);
                } else {
                    return invocation.nextInterceptor();
                }
            }
            return this.interceptMethod.invoke(interceptedObject, invocation);
        } catch (Exception ex) {
            Logger.getLogger(UserInterceptor.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvocationException(ex.getMessage());
        } 
    }

}
