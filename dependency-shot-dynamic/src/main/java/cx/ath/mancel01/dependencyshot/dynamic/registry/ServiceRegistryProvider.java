/*
 *  Copyright 2011 mathieuancelin.
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

package cx.ath.mancel01.dependencyshot.dynamic.registry;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.inject.Singleton;

/**
 *
 * @author mathieuancelin
 */
public class ServiceRegistryProvider implements Provider<ServiceRegistry> {

    @Inject OSGiEnvHolder holder;
    @Inject ServiceRegistryImpl internalService;
    @Inject OSGiServiceRegistryImpl osgiService;

    @Override
    public ServiceRegistry get() {
        if (holder.isOsgi()) {
            return osgiService;
        } else {
            return internalService;
        }
    }

    @Singleton
    public static class OSGiEnvHolder {
        
        private boolean osgi = false;

        public void setOsgi(boolean osgi) {
            this.osgi = osgi;
        }

        public boolean isOsgi() {
            return osgi;
        }
    }
}
