/*
 *  Copyright 2009 mathieuancelin.
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

import cx.ath.mancel01.dependencyshot.api.DSInterceptor;
import cx.ath.mancel01.dependencyshot.api.DSInvocation;
import cx.ath.mancel01.dependencyshot.exceptions.InvocationException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieuancelin
 */
public class UserInterceptor implements DSInterceptor {

    private Method interceptMethod;

    private Object interceptedObject;

    public UserInterceptor(Method method, Object object){
        this.interceptMethod = method;
        this.interceptedObject = object;
    }

    @Override
    public Object invoke(DSInvocation invocation) {
        try {
            return this.interceptMethod.invoke(interceptedObject, invocation);
        } catch (Exception ex) {
            Logger.getLogger(UserInterceptor.class.getName()).log(Level.SEVERE, null, ex);
            throw new InvocationException(ex.getMessage());
        } 
    }

}
