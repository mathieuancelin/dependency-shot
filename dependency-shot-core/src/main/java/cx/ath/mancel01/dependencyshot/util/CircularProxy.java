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

package cx.ath.mancel01.dependencyshot.util;

import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author mathieuancelin
 */
public class CircularProxy implements InvocationHandler {

    private Object delegateObject;

    private InjectorImpl injector;

    private Class clazz;

    public CircularProxy() {
    }

    public void setInjector(InjectorImpl injector) {
        this.injector = injector;
    }

    public void setClazz(Class clazz) {
        System.out.println("New proxy for class "  + clazz.getSimpleName());
        this.clazz = clazz;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Call from circular proxy on method : " + clazz.getSimpleName() + "." + method.getName() + "()");
        if (delegateObject == null) {
            System.out.println("Possible implementations for " + clazz.getSimpleName() + " are => " + injector.getInstanciatedClasses().keySet());
            delegateObject = injector.getInstanciatedClasses().get(clazz);
            injector.getInstanciatedClasses().remove(clazz);
        }
        return method.invoke(delegateObject, args);
    }

}
