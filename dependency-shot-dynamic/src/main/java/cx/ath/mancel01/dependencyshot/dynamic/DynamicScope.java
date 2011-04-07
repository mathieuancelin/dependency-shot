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

package cx.ath.mancel01.dependencyshot.dynamic;

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.CustomScopeHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;

/**
 *
 * @author Mathieu ANCELIN
 */
public class DynamicScope extends CustomScopeHandler {

    @Override
    public Class<? extends Annotation> getScope() {
        return Dynamic.class;
    }

    @Override
    public <T> T getScopedInstance(Class<T> interf, Class<? extends T> clazz, 
            InjectionPoint point, InjectorImpl injector) {
        DynamicProxy proxy = new DynamicProxy(interf, point,
                injector, injector.getInstance(ServiceRegistryImpl.class));
        return (T) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[] {interf}, proxy);
        //return (T) ProxyHelper.createProxy(proxy);
    }

    @Override
    public void reset() {}

    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public boolean isBeanValid(Class from, Class to) {
        return true;
    }
}
