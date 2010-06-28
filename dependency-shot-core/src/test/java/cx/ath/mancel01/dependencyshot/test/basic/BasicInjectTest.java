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

package cx.ath.mancel01.dependencyshot.test.basic;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class BasicInjectTest {
    /**
     * Mock test.
     */
    @Test
    public void testMockedClient() {
        BasicMockService mock = new BasicMockService();
        BasicClient client = new BasicClient(mock);
        client.setService2(mock);
        client.setService3(mock);
        client.go();
        assertTrue(client.getService().isGone());
        assertTrue(client.getService2().isGone());
        assertTrue(client.getService3().isGone());
    }

    @Test
    public void testFluentInjectedClient() {
        DSInjector injector = DependencyShot.getInjector(new BasicFluentBinder());
        BasicClient client = injector.getInstance(BasicClient.class);
        client.go();
        assertTrue(!client.getService().isGone());
        assertTrue(!client.getService3().isGone());
        assertTrue(!client.getService2().isGone());

    }
}
