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

package cx.ath.mancel01.dependencyshot.test.typesafe;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import javax.inject.Provider;

/**
 *
 * @author Mathieu ANCELIN
 */
public class TypesafeModule extends Binder {

    @Override
    public void configureBindings() {
        bind(Service.class).to(ServiceImpl.class);
        bind(Service.class).named("service").providedBy(
            new Provider<Service>() {

                @Override
                public Service get() {
                    return new ServiceImpl();
                }

            });
        bind(Service.class).named("service2").providedBy(
            new Provider<ServiceImpl>() {

                @Override
                public ServiceImpl get() {
                    return new ServiceImpl();
                }

            });
    }

}
