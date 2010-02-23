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
package cx.ath.mancel01.dependecyshot.web.el;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 *
 * @author Mathieu ANCELIN
 */
public class ELLazyInvocationHandler implements InvocationHandler {

    /**
     * The concerned object.
     */
    private Object ret;
    private Method methodFromController;
    private Object caller;
    private Object[] parameters;

    public ELLazyInvocationHandler(Method method,
            Object caller, Object[] parameters) {
        this.methodFromController = method;
        this.caller = caller;
        this.parameters = parameters;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (ret == null) {
            boolean accessible = methodFromController.isAccessible();
            if (!accessible) {
                methodFromController.setAccessible(true);
            }
            try {
                ret = methodFromController.invoke(caller, parameters);
            } finally {
                if (!accessible) {
                    methodFromController.setAccessible(accessible);
                }
            }
        }
        Method methodToInvoke = ret.getClass()
                .getMethod(method.getName(), method.getParameterTypes());
        boolean accessible = methodToInvoke.isAccessible();
        Object loaded = null;
        if (!accessible) {
            methodToInvoke.setAccessible(true);
        } try {
            loaded = methodToInvoke.invoke(ret, args);
        } finally {
            if (!accessible) {
                methodToInvoke.setAccessible(accessible);
            }
        }
        return loaded;
    }
}
