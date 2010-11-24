/*
 *  Copyright 2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.dynamic;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.exceptions.DSIllegalStateException;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author mathieu
 */
public class DynamicTest {

    public static final String PAYPAL = "paypal";
    public static final String CREDITCARD = "card";
    public static final String CASH = "cash";

    @Test
    public void dynamic() {
        DynamicBinder binder = new DynamicBinder() {
            @Override
            public void configure() {
                bindDynamically(PaymentService.class);
            }
        };
        DSInjector injector = DependencyShot.getInjector(binder);
        ServiceRegistry registry = injector.getInstance(ServiceRegistry.class);

        
        registry.registerService(PaymentService.class,
                PayPalServiceImpl.class);
        registry.registerService(PaymentService.class,
                CreditCardServiceImpl.class);

        PaymentService service = injector.
                getInstance(PaymentService.class);

        Assert.assertEquals(PAYPAL, service.pay(123));

        registry.unregisterService(PayPalServiceImpl.class);
        Assert.assertEquals(CREDITCARD, service.pay(123));

        registry.registerService(PaymentService.class,
                PayPalServiceImpl.class);
        Assert.assertEquals(CREDITCARD, service.pay(123));

        registry.swap(PayPalServiceImpl.class);
        Assert.assertEquals(PAYPAL, service.pay(123));

        registry.swap(CashServiceImpl.class);
        Assert.assertEquals(CASH, service.pay(123));
        
        service.toString();
    }

    @Test(expected=DSIllegalStateException.class)
    public void fail() {
        DynamicBinder binder = new DynamicBinder() {
            @Override
            public void configure() {
                bind(PaymentService.class).to(CashServiceImpl.class);
                bindDynamically(PaymentService.class);
            }
        };
        DSInjector injector = DependencyShot.getInjector(binder);
    }

    @Test
    public void binderMixin() {
        DSBinder binder1 = new Binder() {
            @Override
            public void configureBindings() {
                bind(PaymentService.class).named(CASH).to(CashServiceImpl.class);
            }
        };

        DSBinder binder2 = new Binder() {
            @Override
            public void configureBindings() {
                bind(PaymentService.class).named(CREDITCARD).to(CreditCardServiceImpl.class);
            }
        };

        DSBinder binder3 = new Binder() {
            @Override
            public void configureBindings() {
                bind(PaymentService.class).named(PAYPAL).to(PayPalServiceImpl.class);
            }
        };
        
        DynamicBinder binder = new DynamicBinder(binder1, binder2, binder3) {
            @Override
            public void configure() {
                
            }
        };

        DSInjector injector = DependencyShot.getInjector(binder);
    }
}