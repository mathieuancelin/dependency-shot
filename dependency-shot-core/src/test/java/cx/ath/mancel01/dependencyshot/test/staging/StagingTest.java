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

package cx.ath.mancel01.dependencyshot.test.staging;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.Stage;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author Mathieu ANCELIN
 */
public class StagingTest {
    
    @Test
    public void testStagingMock() {
        DSInjector injector = DependencyShot.getInjector(Stage.TEST, new ServiceBinder());
        Module client = injector.getInstance(Module.class);
        assertTrue(client.getServiceResult().equals(Service.MOCK));
    }

    @Test
    public void testStagingDev() {
        DSInjector injector = DependencyShot.getInjector(Stage.DEVELOPEMENT, new ServiceBinder());
        Module client = injector.getInstance(Module.class);
        assertTrue(client.getServiceResult().equals(Service.DEV));
    }

    @Test
    public void testStagingImpl() {
        DSInjector injector = DependencyShot.getInjector(Stage.PRODUCTION, new ServiceBinder());
        Module client = injector.getInstance(Module.class);
        assertTrue(client.getServiceResult().equals(Service.IMPL));
    }
}
