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

package cx.ath.mancel01.dependencyshot.scope.session;

import cx.ath.mancel01.dependencyshot.scope.thread.*;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.CustomScopeHandler;
import java.lang.annotation.Annotation;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Like singleton scope handler but in a thread (ie. one singleton per thread)
 *
 * @author Mathieu ANCELIN
 */
public class SessionScope extends CustomScopeHandler {

    private static final Logger logger = Logger.getLogger(SessionScope.class.getSimpleName());

    private final ThreadLocal<Map<Class<?>, Object>> threadLocalinstances =
            new ThreadLocal<Map<Class<?>, Object>>() {
                @Override
                protected Map<Class<?>, Object> initialValue() {
                    return new HashMap<Class<?>, Object>();
                }
            };

    @Override
    public Class<? extends Annotation> getScope() {
        return ThreadScoped.class;
    }

    @Override
    public <T> T getScopedInstance(Class<T> interf, Class<? extends T> clazz, InjectorImpl injector) {
        if (!interf.isInterface()) {
            logger.warning(new StringBuilder()
                    .append("Sessioncope can't be applied on objects without interfaces. ")
                    .append("Applying standard injection on ")
                    .append(clazz.getSimpleName())
                    .append(".java").toString());
            return injector.createInstance(clazz);
        }
        SessionScopeProxy proxy = new SessionScopeProxy(threadLocalinstances, clazz, injector);
        return (T) Proxy.newProxyInstance(
                    Thread.currentThread().getContextClassLoader(),
                    new Class[]{interf}, proxy);
    }

    @Override
    public void reset() {
        threadLocalinstances.get().clear();
    }

    @Override
    public boolean isDynamic() {
        return true;
    }

    @Override
    public boolean isBeanValid(Class from, Class to) {
        return from.isInterface();
    }
}
