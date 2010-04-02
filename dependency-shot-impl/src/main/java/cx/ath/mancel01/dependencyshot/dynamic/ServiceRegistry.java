/*
 *  Copyright 2010 mathieu.
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

import cx.ath.mancel01.dependencyshot.graph.Binding;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Registry for dynamic services
 *
 * @author Mathieu ANCELIN
 */
public class ServiceRegistry {

    private static final Logger logger = Logger.getLogger(ServiceRegistry.class.getName());

    private Map<Binding, DynamicService> dynamicServices;

    private ServiceProxyFactory factory;

    public ServiceRegistry() {
        dynamicServices = new HashMap<Binding, DynamicService>();
        factory = new ServiceProxyFactory();
    }

    public Object addService(Binding binding, Object instance, InjectorImpl injector) {
        if (!dynamicServices.containsKey(binding)) {
            DynamicService service = new DynamicService(binding, instance, injector);
            dynamicServices.put(binding, service);
            return factory.getProxyInstance(service);
        } else {
            logger.info("Registry already contains " + binding.toString());
            return factory.getProxyInstance(dynamicServices.get(binding));
        }
    }
    
    public Object getService(Binding binding) {
        if (dynamicServices.containsKey(binding)) {
            return factory.getProxyInstance(dynamicServices.get(binding));
        }
        throw new RuntimeException("Can't find service with binding " + binding.toString());
    }

    public void removeService(Binding binding) {
        if (dynamicServices.containsKey(binding)) {
            dynamicServices.remove(binding);
        } else {
            logger.info("Registry doesn't contains " + binding.toString());
        }
    }

    public void changeServiceImpl(Binding binding, Class newImpl) {
        if (dynamicServices.containsKey(binding)) {
            dynamicServices.get(binding).changeImpl(newImpl);
        }
        throw new RuntimeException("Can't find service with binding " + binding.toString());
    }
}
