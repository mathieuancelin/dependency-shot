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

package cx.ath.mancel01.dependencyshot.test.instanceinjection;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author mathieuancelin
 */
public class InstanceInjectionTest {

    @Test
    public void testInstanceInjection() {
        DSInjector injector = DependencyShot.getInjector();
        Module client = new Module();
        client = injector.injectInstance(client);
        assertTrue(client.getProperty1().equals("PROPERTY1"));
        assertTrue(client.getProperty2().equals("PROPERTY2"));
        assertTrue(client.getProperty3().equals("PROPERTY3"));
        assertTrue(client.getProperty4().equals(Module.DEFAULT));
        assertTrue(client.getProperty5().equals("PROPERTY5"));
    }
}