/*
 *  Copyright 2009-2010 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependencyshot.spi;

import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.annotation.Annotation;

/**
 * A class that provides object according to their scope.
 *
 * @author Mathieu ANCELIN
 */
public abstract class CustomScopeHandler {

    public abstract Class<? extends Annotation> getScope();

    public abstract <T> T getScopedInstance(Class<T> from, Class<? extends T> to, InjectorImpl injector);

    public abstract void reset();

    public abstract boolean isDynamic();

    public abstract boolean isBeanValid(Class from, Class to);

}
