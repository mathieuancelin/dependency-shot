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

package cx.ath.mancel01.dependencyshot.scope;

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.CustomScopeHandler;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author Mathieu ANCELIN
 */
public class ScopeInvocationHandler implements InvocationHandler {

    private final CustomScopeHandler handler;
    private final Class interf;
    private final Class clazz;
    private final InjectorImpl injector;
    private final InjectionPoint point;

    public ScopeInvocationHandler(CustomScopeHandler handler,
            Class interf, Class clazz, InjectionPoint p, InjectorImpl injector) {
        this.handler = handler;
        this.interf = interf;
        this.clazz = clazz;
        this.injector = injector;
        this.point = p;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(handler.getScopedInstance(interf, clazz, point, injector), args);
    }

}
