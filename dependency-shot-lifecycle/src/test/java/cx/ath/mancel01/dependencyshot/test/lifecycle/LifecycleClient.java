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

package cx.ath.mancel01.dependencyshot.test.lifecycle;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * Test client.
 * 
 * @author Mathieu ANCELIN
 */
@ManagedBean(value="tests/lifecycleclient")
public class LifecycleClient {

    /**
     * A service.
     */
    private final LifecycleService service;
    /**
     * Another service.
     */
    @Inject
    private LifecycleService service2;
    /**
     * Yet another service.
     */
    private LifecycleService service3;

    /**
     * Injected constructor.
     * @param service injected object.
     */
    @Inject
    public LifecycleClient(LifecycleService service) {
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
    public void setService2(LifecycleService service2) {
        this.service2 = service2;
    }

    /**
     * Injected setter.
     * @param service3 a service.
     */
    @Inject
    public void setService3(LifecycleService service3) {
        this.service3 = service3;
    }

    /**
     * Getter.
     * @return a service.
     */
    public LifecycleService getService() {
        return this.service;
    }

    /**
     * Getter.
     * @return a service.
     */
    public LifecycleService getService2() {
        return service2;
    }

    /**
     * Getter.
     * @return a service.
     */
    public LifecycleService getService3() {
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

    @PostConstruct
    public void load() {
        //System.out.println("Load LifeCycleClient : " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementLoad();
    }
    @PreDestroy
    public void unload() {
        //System.out.println("Unload LifeCycleClient : " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementUnload();
    }
}
