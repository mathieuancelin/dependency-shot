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

package cx.ath.mancel01.dependencyshot.dsl;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author mathieuancelin
 */
public class DslTest {

    @Test
    public void testDsl() {
        DSInjector injector = DependencyShot.getSpecificConfigurator()
                .getInjector("src/main/resources/dsl.groovy");
        BasicClient client = injector.getInstance(BasicClient.class);
        client.go();
        assertTrue(!client.getService().isGone());
        assertTrue(!client.getService3().isGone());
        assertTrue(!client.getService2().isGone());


    }
}