/*
 *  Copyright 2009-2010 Mathieu ANCELIN
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
import java.util.Map;
import javax.interceptor.InvocationContext;

/**
 * Invocation for interceptors.
 * 
 * @author Mathieu ANCELIN
 */
public class DSInvocation implements InvocationContext {
    /**
     * The invocation's bean.
     */
    private Object bean;
    /**
     * The invocation's interceptors.
     */
    private DSInterceptor[] interceptors;
    /**
     * The invocation's method.
     */
    private Method method;
    /**
     * The invocation's args.
     */
    private Object[] args;
    /**
     * The interceptor index.
     */
    private int index;
    /**
     * Constructor.
     * @param bean the invocation's bean.
     * @param interceptors the invocation's interceptors.
     * @param method the invocation's method.
     * @param args the invocation's args.
     */
    public DSInvocation(final Object bean, DSInterceptor[] interceptors,
            final Method method, Object[] args) {
        this.bean = bean;
        this.interceptors = interceptors;
        this.method = method;
        this.args = args;
    }
    /**
     * @return the invocation's method.
     */
    @Override
    public final Method getMethod() {
        return method;
    }
    /**
     * @return the invocation of the next interceptor.
     */
    public final Object nextInterceptor() {
        try {
            return interceptors[index++].invoke(this);
        } finally {
            index--;
        }
    }
    /**
     * @return the invocation of the next interceptor.
     */
    @Override
    public final Object proceed() {
        try {
            return interceptors[index++].invoke(this);
        } finally {
            index--;
        }
    }

    /**
     * @return the invocation's bean.
     */
    @Override
    public final Object getTarget() {
        return bean;
    }

    @Override
    public final Object getTimer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    /**
     * @return the invocation's parameters.
     */
    @Override
    public final Object[] getParameters() {
        return args;
    }

    @Override
    public final void setParameters(Object[] params) {
        this.args = params;
    }

    @Override
    public final Map<String, Object> getContextData() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
