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

package cx.ath.mancel01.dependencyshot.test.aop.v2.all;

import cx.ath.mancel01.dependencyshot.test.aop.v2.ResultSingleton;
import javax.inject.Inject;
import javax.inject.Singleton;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public class MethodPatternInterceptor implements MethodInterceptor {

    @Inject
    private ResultSingleton singleton;

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        try {
            singleton.incrementBeforeCall();
            return mi.proceed();
        } finally {
            singleton.incrementAfterCall();
        }
    }
}
