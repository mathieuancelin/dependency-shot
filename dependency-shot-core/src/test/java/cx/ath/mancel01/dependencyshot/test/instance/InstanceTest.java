/*
 *  Copyright 2009-2010 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependencyshot.test.instance;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceTest {
    /**
     * Injection test.
     */
    @Test
    public void testSpecificInstancesInjection() {
        DSInjector injector = DependencyShot.getInjector(new InstanceBinder());
        Module module1 = injector.getInstance(InstanceModule.class);
        Module module2 = injector.getInstance(InstanceModule2.class);
        Module module3 = injector.getInstance(InstanceModule3.class);
        module1.start();
        module2.start();
        module3.start();
        assertTrue(module1.getName()
                .equals(InstanceBinder.MY_MODULE));
        assertTrue(module2.getName()
                .equals(InstanceBinder.MY_OTHER_MODULE));
        assertTrue(module3.getName()
                .equals(InstanceBinder.YAM));
    }

    @Test
    public void testEasySpecificInstancesInjection() {
        DSInjector injector = DependencyShot.getInjector(new EasyInstanceBinder());
        Module module1 = injector.getInstance(InstanceModule.class);
        Module module2 = injector.getInstance(InstanceModule2.class);
        Module module3 = injector.getInstance(InstanceModule3.class);
        module1.start();
        module2.start();
        module3.start();
        assertTrue(module1.getName()
                .equals(EasyInstanceBinder.MY_MODULE));
        assertTrue(module2.getName()
                .equals(EasyInstanceBinder.MY_OTHER_MODULE));
        assertTrue(module3.getName()
                .equals(EasyInstanceBinder.YAM));
    }
}
