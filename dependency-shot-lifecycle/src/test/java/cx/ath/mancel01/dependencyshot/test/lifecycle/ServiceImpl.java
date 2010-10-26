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

package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.aop.annotation.Interceptors;
import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 *
 * @author mathieuancelin
 */
@ManagedBean
@Interceptors(Interceptor.class)
public class ServiceImpl implements Service {

    @Override
    public void doSomething() {
        System.out.println("doing something ...");
    }

    @PostConstruct
    public void init() {
        System.out.println("starting service ...");
    }

    @PreDestroy
    public void tearDown() {
        System.out.println("stoping service ...");
    }

}
