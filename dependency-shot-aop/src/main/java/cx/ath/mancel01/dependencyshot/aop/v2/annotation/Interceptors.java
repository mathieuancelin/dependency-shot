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

package cx.ath.mancel01.dependencyshot.aop.v2.annotation;

import javax.interceptor.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>Declares an ordered list of interceptors for a target class or
 * method of a target class.</p>
 *
 * <pre>
 * &#064;Interceptors(ValidationInterceptor.class)
 * public class Order { ... }
 * </pre>
 *
 * <pre>
 * &#064;Interceptors({ValidationInterceptor.class, SecurityInterceptor.class})
 * public void updateOrder(Order order) { ... }
 * </pre>
 *
 * <p>Only method interception or timeout method interception may be specified
 * by a method-level <tt>&#064;Interceptors</tt> declaration.</p>
 *
 * @see javax.interceptor.ExcludeClassInterceptors
 * @see javax.interceptor.ExcludeDefaultInterceptors
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Interceptors {
    /**
     * @return interceptor classes.
     */
    Class[] value();
 }
