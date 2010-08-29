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

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
/**
 * <p>Specifies that an annotation type is an interceptor binding type.</p>
 *
 * <pre>
 * &#064;Inherited
 * &#064;InterceptorBinding
 * &#064;Target({TYPE, METHOD})
 * &#064;Retention(RUNTIME)
 * public &#064;interface Valid {}
 * </pre>
 *
 * <p>Interceptor bindings
 * are intermediate annotations that may be used to associate
 * interceptors with target beans.</p>
 *
 * <p>The interceptor bindings of an interceptor are specified by annotating
 * the interceptor class with the binding types and
 * {@link javax.interceptor.Interceptor &#064;Interceptor}.</p>
 *
 * <pre>
 * &#064;Valid &#064;Interceptor
 * public class ValidationInterceptor { ... }
 * </pre>
 *
 * <p>An interceptor may specify multiple interceptor bindings.</p>
 *
 * <p>An interceptor binding of a bean
 * may be declared by annotating the bean class, or a method of the bean class,
 * with the interceptor binding type.</p>
 *
 * <pre>
 * &#064;Valid
 * public class Order { ... }
 * </pre>
 *
 * <pre>
 * &#064;Valid &#064;Secure
 * public void updateOrder(Order order) { ... }
 * </pre>
 *
 * <p>A bean class or method of a bean class may declare multiple interceptor
 * bindings.</p>
 *
 * <p>An interceptor binding type may declare other interceptor bindings.</p>
 *
 * <pre>
 * &#064;Inherited
 * &#064;InterceptorBinding
 * &#064;Target({TYPE, METHOD})
 * &#064;Retention(RUNTIME)
 * &#064;Valid
 * public &#064;interface Secure {}
 * </pre>
 *
 * <p>Interceptor bindings are transitive&mdash;an interceptor binding declared
 * by an interceptor binding type is inherited by all beans and other interceptor
 * binding types that declare that interceptor binding type.</p>
 *
 * @see javax.interceptor.Interceptor
 */
@Target(ANNOTATION_TYPE)
@Retention(RUNTIME)
@Documented
public @interface InterceptorBinding {}
