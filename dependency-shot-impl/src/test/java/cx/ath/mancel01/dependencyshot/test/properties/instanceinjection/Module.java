/*
 *  Copyright 2010 mathieuancelin.
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

package cx.ath.mancel01.dependencyshot.test.properties.instanceinjection;

import cx.ath.mancel01.dependencyshot.api.annotations.Property;
import javax.inject.Inject;

/**
 * Tests for built-in properties injection system.
 *
 * @author Mathieu ANCELIN
 */
public class Module {

    public static final String DEFAULT = "DEFAULT";

    @Inject @Property
    private String property1;

    @Inject @Property(name="property2")
    private String property2;

    @Inject @Property(name="property", value=DEFAULT)
    private String property3;

    @Inject @Property(value=DEFAULT)
    private String property4;

    @Inject @Property(name="property5", value=DEFAULT, bundle="config")
    private String property5;

    public String getProperty1() {
        return property1;
    }

    public String getProperty2() {
        return property2;
    }

    public String getProperty3() {
        return property3;
    }

    public String getProperty4() {
        return property4;
    }

    public String getProperty5() {
        return property5;
    }
}
