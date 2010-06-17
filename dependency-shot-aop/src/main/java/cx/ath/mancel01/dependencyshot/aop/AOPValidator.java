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

package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.spi.ImplementationValidator;
import javax.annotation.ManagedBean;

/**
 * ManagedBean validator for lifecycle handling
 *
 * @author Mathieu ANCELIN
 */
public class AOPValidator extends ImplementationValidator {

    private boolean isManagedBean(Object o) {
        return isManagedBean(o.getClass());
    }

    private boolean isManagedBean(Class clazz) {
        return clazz.isAnnotationPresent(ManagedBean.class);
    }

    @Override
    public boolean isValid(Object o) {
        return isManagedBean(o);
    }
}
