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
 * <p>Used to exclude default interceptors for a target class or
 * for a business method or timeout method of a target class.</p>
 *
 * <pre>
 * &#064;ExcludeDefaultInterceptors
 * &#064;Interceptors(ValidationInterceptor.class)
 * public class Order { ... }
 * </pre>
 *
 * <pre>
 * &#064;ExcludeDefaultInterceptors
 * public void updateOrder(Order order) { ... }
 * </pre>
 *
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcludeDefaultInterceptors {}
