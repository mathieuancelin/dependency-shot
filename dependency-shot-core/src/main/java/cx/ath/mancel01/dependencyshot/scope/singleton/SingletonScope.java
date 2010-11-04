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

package cx.ath.mancel01.dependencyshot.scope.singleton;

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.spi.CustomScopeHandler;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Singleton;

/**
 *
 * @author Mathieu ANCELIN
 */
public class SingletonScope extends CustomScopeHandler {

    /**
     * Singleton scoped object singletonContext.
     */
    private Map<Class<?>, Object> singletonContext;

    public SingletonScope() {
        singletonContext = new HashMap<Class<?>, Object>();
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return Singleton.class;
    }

    @Override
    public <T> T getScopedInstance(Class<T> interf, Class<? extends T> clazz,
            InjectionPoint p, InjectorImpl injector) {
        // check if the singleton is present in the singleton context
        T result = clazz.cast(singletonContext.get(clazz));
        // if not, create one
        if (result == null) {
            result = injector.createInstance(clazz);
            singletonContext.put(clazz, result);
        }
        return result;
    }

    @Override
    public void reset() {
        singletonContext.clear();
    }

    @Override
    public boolean isDynamic() {
        return false;
    }

    @Override
    public boolean isBeanValid(Class from, Class to) {
        return true;
    }

}
