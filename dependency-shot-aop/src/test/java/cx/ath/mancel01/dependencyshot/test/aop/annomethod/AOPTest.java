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

package cx.ath.mancel01.dependencyshot.test.aop.annomethod;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.test.aop.ResultSingleton;
import cx.ath.mancel01.dependencyshot.test.aop.Service;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class AOPTest {


    @Test
    public void testAnnotationsOnMethods() throws Exception {
        DSInjector injector = DependencyShot.getInjector(new Binder() {
            @Override
            public void configureBindings() {
                bind(Service.class).to(HelloService.class);
            }
        });

        Service service = injector.getInstance(Service.class);
        ResultSingleton result = injector.getInstance(ResultSingleton.class);
        service.hello();
        Assert.assertTrue(result.getBeforeCall() == 1);
        Assert.assertTrue(result.getAfterCall() == 1);
        service.something();
        service.hello();
        Assert.assertTrue(result.getBeforeCall() == 2);
        Assert.assertTrue(result.getAfterCall() == 2);
        result.reset();
        // test exclude
        service.goodbye();
        Assert.assertTrue(result.getBeforeCall() == 0);
        Assert.assertTrue(result.getAfterCall() == 0);
    }
}
