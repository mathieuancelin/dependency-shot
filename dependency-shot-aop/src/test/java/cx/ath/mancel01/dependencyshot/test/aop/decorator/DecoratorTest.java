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

package cx.ath.mancel01.dependencyshot.test.aop.decorator;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.aop.AOPBinder;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Assert;
import org.junit.Test;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class DecoratorTest {


    @Test
    public void testDecorators() throws Exception {
        DSInjector injector = DependencyShot.getInjector(new AOPBinder() {
            @Override
            public void configure() {
                bind(Service.class).to(PaymentService.class);
                decorate(Service.class).with(ServiceDecorator.class);
            }
        });
        injector.allowCircularDependencies(true);
        Service service = injector.getInstance(Service.class);
        ServiceDecorator dec = injector.getInstance(ServiceDecorator.class);
        PaymentService impl = injector.getInstance(PaymentService.class);
        service.pay(123.5);
        Assert.assertTrue(dec.calls == 1);
        Assert.assertTrue(impl.calls == 1);
    }

    @Test
    public void testRegisterDecorators() throws Exception {
        DSInjector injector = DependencyShot.getInjector(new AOPBinder() {
            @Override
            public void configure() {
                bind(Service.class).to(PaymentService.class);
                registerDecorator(ServiceDecorator.class);
            }
        });
        injector.allowCircularDependencies(true);
        Service service = injector.getInstance(Service.class);
        ServiceDecorator dec = injector.getInstance(ServiceDecorator.class);
        PaymentService impl = injector.getInstance(PaymentService.class);
        service.pay(123.5);
        Assert.assertTrue(dec.calls == 1);
        Assert.assertTrue(impl.calls == 1);
    }
}
