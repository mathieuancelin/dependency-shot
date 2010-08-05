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

package cx.ath.mancel01.dependencyshot.test.importbinders;

import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Mathieu ANCELIN
 */
public class InjectedClass {

    @Inject
    private Service1 service1;
    @Inject @Named("double")
    private Service1 service11;
    @Inject
    private Service2 service2;
    @Inject @Named("double")
    private Service2 service22;

    public Service1 getService1() {
        return service1;
    }

    public Service1 getService11() {
        return service11;
    }

    public Service2 getService2() {
        return service2;
    }

    public Service2 getService22() {
        return service22;
    }

}
