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

package cx.ath.mancel01.dependencyshot.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

/**
 *
 * @author Mathieu ANCELIN
 */
public class MethodInterceptorWrapper implements MethodInterceptor {

    private final MethodInterceptor interceptor;

    private Collection<Method> interceptedMethods = new ArrayList<Method>();

    public MethodInterceptorWrapper(MethodInterceptor interceptor) {
        this.interceptor = interceptor;
    }

    public boolean canBeAppliedOn(Method method) {
        if (interceptedMethods.isEmpty()) {
            return true;
        } else {
            return interceptedMethods.contains(method);
        }
    }

    public void addInterceptedMethod(Method method) {
        this.interceptedMethods.add(method);
    }

    @Override
    public Object invoke(MethodInvocation mi) throws Throwable {
        return interceptor.invoke(mi);
    }

}
