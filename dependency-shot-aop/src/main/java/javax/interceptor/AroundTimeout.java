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

import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Retention;
/**
 * <p>Defines an interceptor method that interposes on timeout methods.
 * May be applied to any non-final, non-static method with a single
 * parameter of type {@link javax.interceptor.InvocationContext} and
 * return type {@link java.lang.Object} of the target class
 * (or superclass) or of any interceptor class.</p>
 *
 * <pre>
 * &#064;AroundTimeout
 * public Object interceptTimeout(InvocationContext ctx) throws Exception { ... }
 * </pre>
 *
 * <p>A class may not declare more than one <tt>&#064;AroundTimeout</tt>
 * method.</p>
 *
 * <p>An <tt>&#064;AroundTimeout</tt> method can invoke any component or
 * resource that its corresponding timeout method can invoke.</p>
 *
 * <p>{@link javax.interceptor.InvocationContext#getTimer()} allows any
 * <tt>&#064;AroundTimeout</tt> method to retrieve the timer object
 * associated with the timeout.</p>
 *
 * <p><tt>&#064;AroundTimeout</tt> method invocations occur within the same
 * transaction and security context as the timeout method on which they are
 * interposing.</p>
 *
 * <p><tt>&#064;AroundTimeout</tt> methods may throw any exceptions that are
 * allowed by the throws clause of the timeout method on which they are
 * interposing. They may catch and suppress exceptions and recover
 * by calling {@link javax.interceptor.InvocationContext#proceed()}.</p>
 *
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AroundTimeout {}
