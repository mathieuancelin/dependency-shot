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

package cx.ath.mancel01.dependencyshot.test.aop.v2.annoclass;

import cx.ath.mancel01.dependencyshot.test.aop.v2.Service;
import cx.ath.mancel01.dependencyshot.test.aop.v2.HelloInterceptor;
import cx.ath.mancel01.dependencyshot.aop.v2.annotation.ExcludeInterceptors;
import cx.ath.mancel01.dependencyshot.aop.v2.annotation.Interceptors;

/**
 *
 * @author Mathieu ANCELIN
 */
@Interceptors(HelloInterceptor.class)
public class HelloService implements Service {

    @Override
    public void hello() {
        System.out.println("----------> Hello World!");
    }

    @ExcludeInterceptors
    @Override
    public void goodbye() {
        System.out.println("----------> Goodbye!");
    }

    @Override
    public void something() {
        System.out.println("----------> Something");
    }

}
