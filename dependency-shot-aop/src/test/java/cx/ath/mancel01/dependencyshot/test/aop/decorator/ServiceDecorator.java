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

package cx.ath.mancel01.dependencyshot.test.aop.decorator;

import cx.ath.mancel01.dependencyshot.aop.annotation.Decorator;
import cx.ath.mancel01.dependencyshot.aop.annotation.Delegate;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author mathieu
 */
@Singleton
@Decorator
public class ServiceDecorator implements Service {

    public int calls = 0;

    @Inject @Delegate
    private Service service;

    @Override
    public void pay(double price) {
        calls++;
        System.out.println(service);
        System.out.println("The user exists ...");
        service.pay(price);
    }
}
