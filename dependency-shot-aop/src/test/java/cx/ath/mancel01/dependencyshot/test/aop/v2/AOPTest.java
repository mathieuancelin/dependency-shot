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

package cx.ath.mancel01.dependencyshot.test.aop.v2;

import cx.ath.mancel01.dependencyshot.aop.v2.MethodInvocationHandler;
import cx.ath.mancel01.dependencyshot.aop.v2.ProxyHelper;
import java.util.ArrayList;
import java.util.List;
import org.aopalliance.intercept.MethodInterceptor;
import org.junit.Test;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class AOPTest {

    @Test
    public void testAOPV2() {
        List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();
        interceptors.add(new HelloInterceptor());
        interceptors.add(new HelloInterceptor());
        interceptors.add(new HelloInterceptor());
        Service service = new HelloService();
        MethodInvocationHandler handler = new MethodInvocationHandler(
                interceptors, Service.class, HelloService.class, service);
        Service aopService =ProxyHelper.createProxy(
                Service.class, HelloService.class, service, handler);
        aopService.hello();
        aopService.hello();
        aopService.goodbye();
    }
}
