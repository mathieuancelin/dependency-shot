/*
 *  Copyright 2009-2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.dynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Observable;
import java.util.Observer;

/**
 * Proxy for dynamic services;
 *
 * @author Mathieu ANCELIN
 */
public class ServiceProxy  implements InvocationHandler, Observer  {

    private Event lastEvent = null;
    
    private DynamicService service;

    private Object serviceInstance = null;

    public ServiceProxy(DynamicService service) {
        this.service = service;
        this.serviceInstance = service.getActualInstance();
        service.addObserver(this);
    }

    private Object getActualInstance() {
        return service.getActualInstance();
    }

    @Override
    public final Object invoke(Object o, Method method, Object[] os) throws Throwable {
        return method.invoke(serviceInstance, os);
    }

    @Override
    public final void update(Observable o, Object event) {
        this.lastEvent = (Event) event;
        this.serviceInstance = lastEvent.getValue();
    }
}
