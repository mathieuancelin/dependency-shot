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

package cx.ath.mancel01.dependencyshot.dynamic;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.dynamic.config.OSGiConfigurator;
import cx.ath.mancel01.dependencyshot.dynamic.config.Publish;
import cx.ath.mancel01.dependencyshot.dynamic.event.AbstractServiceEvent;
import cx.ath.mancel01.dependencyshot.dynamic.event.BundleContainerInitialized;
import cx.ath.mancel01.dependencyshot.dynamic.event.BundleContainerShutdown;
import cx.ath.mancel01.dependencyshot.dynamic.event.ServiceArrival;
import cx.ath.mancel01.dependencyshot.dynamic.event.ServiceChanged;
import cx.ath.mancel01.dependencyshot.dynamic.event.ServiceDeparture;
import cx.ath.mancel01.dependencyshot.dynamic.registry.ServiceRegistry;
import cx.ath.mancel01.dependencyshot.event.InjectorStoppedEvent;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.net.URL;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceEvent;
import org.osgi.framework.ServiceListener;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author mathieuancelin
 */
public class DependencyShotActivator implements BundleActivator,
        BundleListener, ServiceListener{

    public static final String DYNAMIC_BINDER = "Dynamic-Binder";
    public static final String DEPENDENCY_SHOT_ENABLED = "Dependency-Shot-Enabled";

    private Map<Long, InjectorImpl> injectors = new HashMap<Long, InjectorImpl>();
    private BundleContext context;

    @Override
    public void start(BundleContext bc) throws Exception {
        this.context = bc;
        for (Bundle bundle : bc.getBundles()) {
            if (Bundle.ACTIVE == bundle.getState()) {
                startContainer(bundle);
            }
        }
        bc.addBundleListener(this);
        bc.addServiceListener(this);
    }

    @Override
    public void stop(BundleContext bc) throws Exception {
        for (Long bundleId : injectors.keySet()) {
            stopContainer(bc.getBundle(bundleId));
        }
    }

    @Override
    public void bundleChanged(BundleEvent be) {
        switch (be.getType()) {
            case BundleEvent.STARTED:
                startContainer(be.getBundle());
                break;
            case BundleEvent.STOPPED:
                stopContainer(be.getBundle());
                break;
        }
        for (InjectorImpl injector : injectors.values()) {
            injector.fireAsync(be);
        }
    }

    @Override
    public void serviceChanged(ServiceEvent se) {
        ServiceReference ref = se.getServiceReference();
        AbstractServiceEvent serviceEvent = null;
        switch (se.getType()) {
            case ServiceEvent.MODIFIED:
                serviceEvent =
                    new ServiceChanged(ref, context);
                break;
            case ServiceEvent.REGISTERED:
                serviceEvent =
                    new ServiceArrival(ref, context);
                break;
            case ServiceEvent.UNREGISTERING:
                serviceEvent =
                    new ServiceDeparture(ref, context);
                break;
        }
        if (serviceEvent != null) {
            for (InjectorImpl injector : injectors.values()) {
                injector.fireAsync(serviceEvent);
            }
        }
    }

    private void stopContainer(Bundle bundle) {
        if (injectors.containsKey(bundle.getBundleId())) {
            System.out.println("Stopping Dependency-Shot container for bundle " + bundle.getSymbolicName());
            InjectorImpl injector = injectors.get(bundle.getBundleId());
            injector.fire(new BundleContainerShutdown(bundle.getBundleContext()));
            injector.triggerLifecycleDestroyCallbacks();
            injector.getInstance(InjectorStoppedEvent.class).fire();
        }
    }

    private void startContainer(Bundle bundle) {
        Dictionary dict = bundle.getHeaders();
        if (dict.get(DEPENDENCY_SHOT_ENABLED) != null) {
            String binderName = (String) dict.get(DYNAMIC_BINDER);
            Class binderClazz = null;
            try {
                binderClazz = bundle.loadClass(binderName);
            } catch (ClassNotFoundException ex) {
            }
            DSBinder binder = null;
            if (binderClazz != null) {
                try {
                    binder = (DSBinder) binderClazz.newInstance();
                } catch (Exception ex) {
                }
            }
            System.out.println("Starting Dependency-Shot container for bundle " + bundle.getSymbolicName());
            InjectorImpl injector = null;
            if (binder != null) {
                injector = DependencyShot
                        .configurator(OSGiConfigurator.class)
                        .context(bundle.getBundleContext())
                        .withBinder(binder)
                        .getInjector();
            } else {
                injector = DependencyShot
                        .configurator(OSGiConfigurator.class)
                        .context(bundle.getBundleContext())
                        .getInjector();
            }
            List<String> discoveredClasses = new ArrayList<String>();
            Enumeration beanClasses = bundle.findEntries("", "*.class", true);
            if (beanClasses != null) {
                while (beanClasses.hasMoreElements()) {
                    URL url = (URL) beanClasses.nextElement();
                    String clazz = url.getFile().substring(1).replace("/", ".").replace(".class", "");
                    discoveredClasses.add(clazz);
                }
            }
            ServiceRegistry reg = injector.getInstance(ServiceRegistry.class);
            for (String clazzName : discoveredClasses) {
                try {
                    Class<?> clazz = bundle.loadClass(clazzName);
                    if (clazz.isAnnotationPresent(Publish.class)) {
                        for (Class<?> itf : clazz.getInterfaces()) {
                            reg.registerService(itf, clazz);
                        }
                    }
                } catch (ClassNotFoundException ex) {
                }
            }
            injector.fire(new BundleContainerInitialized(bundle.getBundleContext()));
            injectors.put(bundle.getBundleId(), injector);
        }
    }
}
