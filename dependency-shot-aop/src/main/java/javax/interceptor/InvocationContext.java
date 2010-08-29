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

package javax.interceptor;

import java.lang.reflect.Method;
import java.util.Map;
/**
 * <p>Exposes context information about the intercepted invocation and operations
 * that enable interceptor methods to control the behavior of the invocation chain.</p>
 *
 * <pre>
 *
 *    &#064;AroundInvoke
 *    public Object logInvocation(InvocationContext ctx) throws Exception {
 *       String class = ctx.getMethod().getDeclaringClass().getName();
 *       String method = ctx.getMethod().getName();
 *       Logger.global.entering(class, method, ctx.getParameters());
 *       try {
 *          Object result = ctx.proceed();
 *          Logger.global.exiting(class, method, result);
 *          return result;
 *       }
 *       catch (Exception e) {
 *          Logger.global.throwing(class, method, e);
 *          throw e;
 *       }
 *
 *    }
 *
 * </pre>
 */
public interface InvocationContext {
    /**
     * Returns the target instance.
     *
     * @return the target instance
     */
    Object getTarget();
    /**
     * Returns the method of the target class for which the interceptor
     * was invoked.  For method interceptors, the method of the
     * target class is returned. For lifecycle callback interceptors,
     * a null value is returned.
     *
     * @return the method, or a null value
     */
    Method getMethod();
    /**
     * Returns the parameter values that will be passed to the method of
     * the target class. If {@code setParameters()} has been called,
     * {@code getParameters()} returns the values to which the parameters
     * have been set.
     *
     * @return the parameter values, as an array
     *
     * @exception java.lang.IllegalStateException if invoked within
     * a lifecycle callback method.
     */
    Object[] getParameters();
    /**
     * Sets the parameter values that will be passed to the method of the
     * target class.
     *
     * @exception java.lang.IllegalStateException if invoked within
     * a lifecycle callback method.
     *
     * @exception java.lang.IllegalArgumentException if the types of the
     * given parameter values do not match the types of the method parameters,
     * or if the number of parameters supplied does not equal the number of
     * method parameters.
     *
     * @param params the parameter values, as an array
     */
    void setParameters(Object[] params);
    /**
     * Proceed to the next interceptor in the interceptor chain.
     * Return the result of the next method invoked, or a null
     * value if the method has return type void, return.
     *
     * @return the return value of the next method in the chain
     */
    Object proceed() throws Exception;
} 
