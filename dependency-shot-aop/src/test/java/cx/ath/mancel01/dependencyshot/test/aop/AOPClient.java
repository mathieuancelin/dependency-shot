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

package cx.ath.mancel01.dependencyshot.test.aop;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

/**
 * Test client.
 * 
 * @author Mathieu ANCELIN
 */
@ManagedBean
public class AOPClient {

    /**
     * A service.
     */
    private final AOPService service;
    /**
     * Another service.
     */
    @Inject
    private AOPService service2;
    /**
     * Yet another service.
     */
    private AOPService service3;

    /**
     * Injected constructor.
     * @param service injected object.
     */
    @Inject
    public AOPClient(AOPService service) {
        super();
        this.service = service;
    }

    /**
     * Business method.
     */
    public void go() {
        service.go();
        service2.go();
        service3.go();
    }

    /**
     * Setter.
     * @param service2 a service.
     */
    public void setService2(AOPService service2) {
        this.service2 = service2;
    }

    /**
     * Injected setter.
     * @param service3 a service.
     */
    @Inject
    public void setService3(AOPService service3) {
        this.service3 = service3;
    }

    /**
     * Getter.
     * @return a service.
     */
    public AOPService getService() {
        return this.service;
    }

    /**
     * Getter.
     * @return a service.
     */
    public AOPService getService2() {
        return service2;
    }

    /**
     * Getter.
     * @return a service.
     */
    public AOPService getService3() {
        return service3;
    }

    /**
     * Getter.
     * @return a string.
     */
    public String getString() {
        return service.getString();
    }

    /**
     * Getter.
     * @return an int.
     */
    public int getInt() {
        return service.getInt();
    }

    /**
     * Setter.
     * @param str a string.
     */
    public void setString(String str) {
        service.setString(str);
    }
}
