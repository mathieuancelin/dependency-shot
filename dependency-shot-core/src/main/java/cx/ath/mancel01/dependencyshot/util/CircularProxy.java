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
import cx.ath.mancel01.dependencyshot.injection.handlers.CircularConstructorHandler;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Map;

/**
 *
 * @author mathieuancelin
 */
public class CircularProxy implements InvocationHandler {

    private Object delegateObject;

    private InjectorImpl injector;

    private Class clazz;

    private Map<Class<?>, Object> circularConstructorArgumentsInstances;

    private CircularConstructorHandler handler;

    public CircularProxy() {
        handler = new CircularConstructorHandler();
    }

    public void setInjector(InjectorImpl injector) {
        this.injector = injector;
    }

    public void setClazz(Class clazz) {
        System.out.println("New proxy created for class "  + clazz.getSimpleName());
        this.clazz = clazz;
    }

    public void setCircularConstructorArgumentsInstances(Map<Class<?>, Object> circularConstructorArgumentsInstances) {
        this.circularConstructorArgumentsInstances = circularConstructorArgumentsInstances;
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (delegateObject == null) {
            System.out.println("Lazy creation from proxy of " + clazz.getSimpleName() +".java");
            System.out.println("Possible injected instances are " + circularConstructorArgumentsInstances);
            delegateObject = handler.getConstructedInstance(clazz, circularConstructorArgumentsInstances);
            if (delegateObject == null) {
                System.out.println("Instance of " + clazz.getSimpleName() + ".java is null :(");
            }
        }
        return method.invoke(delegateObject, args);
    }

}
