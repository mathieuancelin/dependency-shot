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

package cx.ath.mancel01.dependencyshot.injection;

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import java.lang.annotation.Annotation;
import java.lang.reflect.Member;
import java.lang.reflect.Type;
import java.util.Set;

/**
 * Data for an injection point.
 *
 * @author Mathieu ANCELIN
 */
public class InjectionPointImpl implements InjectionPoint {
    /**
     * The injected type.
     */
    private Type type;
    /**
     * Present annotations.
     */
    private Set<Annotation> annotations;
    /**
     * The injected member.
     */
    private Member member;
    /**
     * The injected class.
     */
    private Class injectedClass;
    /**
     * Constructor.
     * 
     * @param type
     * @param annotations
     * @param member
     * @param injectedClass
     */
    public InjectionPointImpl(Type type,
            Set<Annotation> annotations,
            Member member,
            Class injectedClass) {
        this.type = type;
        this.annotations = annotations;
        this.member = member;
        this.injectedClass = injectedClass;
    }
    /**
     * @return the injected type.
     */
    @Override
    public final Type getType() {
        return this.type;
    }
    /**
     * @return annotations presents.
     */
    @Override
    public final Set<Annotation> getAnnotations() {
        return this.annotations;
    }
    /**
     * @return the injected member.
     */
    @Override
    public final Member getMember() {
        return this.member;
    }
    /**
     * @return the type of the bean.
     */
    @Override
    public final Class getBeanClass() {
        return this.member.getDeclaringClass();
    }
    /**
     * @return injected class.
     */
    public final Class getInjectedClass() {
        return injectedClass;
    }
    /**
     * @param injectedClass new value for injected class.
     */
    public final void setInjectedClass(Class injectedClass) {
        this.injectedClass = injectedClass;
    }
}
