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

package javax.annotation;

import java.lang.annotation.*;
import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

/**
 * The ManagedBean annotation marks a POJO (Plain Old Java Object) as a
 * ManagedBean.A ManagedBean supports a small set of basic services such as
 * resource injection, lifecycle callbacks and interceptors.
 *
 * @since Common Annotations 1.1
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface ManagedBean {
    /**
     * The name of the Managed Bean. Managed Bean names must be unique within a
     * Java EE module. For each named Managed Bean, Java EE containers must make
     * available the following entries in JNDI, using the same naming scheme used
     * for EJB components.
     * <p>
     * In the application namespace: <p>
     * java:app/&lt;module-name&gt;/&lt;bean-name&gt; <p>
     * In the module namespace of the module containing the Managed Bean:
     * <p> java:module/&lt;bean-name&gt;
     *
     */
    String value() default "";
}
