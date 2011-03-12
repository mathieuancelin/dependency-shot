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
package cx.ath.mancel01.dependencyshot.event;

import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.api.event.EventListener;
import cx.ath.mancel01.dependencyshot.api.event.Event;
import cx.ath.mancel01.dependencyshot.api.event.EventManager;
import cx.ath.mancel01.dependencyshot.api.event.Observes;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * A manager for internal event.
 * The generated events will be broadcasted to SPI extension points.
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public class EventManagerImpl implements EventManager {

    private static final Logger logger = Logger.getLogger(EventManagerImpl.class.getSimpleName());
    
    private static final int NTHREADS = 10;

    private final ExecutorService exec = Executors.newFixedThreadPool(NTHREADS);

    private HashMap<Class<?>, ArrayList<EventListener>> listeners;
    
    private InjectorImpl injector;

    public EventManagerImpl() {
        listeners = new HashMap<Class<?>, ArrayList<EventListener>>();
    }

    @Inject
    public void setInjector(InjectorImpl injector) {
        this.injector = injector;
    }

    public void registerListeners(Collection<Class<?>> listeners) {
        for (Class listener : listeners) {
            registerListener(listener);
        }
    }

    public void registerListener(Class<?> toRegister) {
        if (EventListener.class.isAssignableFrom(toRegister)) {
           Type[] interfaces = toRegister.getGenericInterfaces();
            Class<? extends Event> type = null;
            for (Type interf : interfaces) {
                if (EventListener.class.isAssignableFrom((Class) ((ParameterizedType) interf).getRawType())) {
                    type = (Class<? extends Event>) ((ParameterizedType) interf).getActualTypeArguments()[0];
                }
            }
            if (type != null) {
                if (!listeners.containsKey(type)) {
                    listeners.put(type, new ArrayList<EventListener>());
                }
                listeners.get(type).add(new EventListenerDecorator((Class<? extends EventListener>) toRegister, injector));
            }
        } else {
            Map<Class, List<Method>> observerMethods = new HashMap<Class, List<Method>>();
            for (Method m : toRegister.getMethods()) {
                if (m.getParameterTypes().length == 1) {
                    Class<?> type = m.getParameterTypes()[0];
                    Annotation[] annotations = m.getParameterAnnotations()[0];
                    for (Annotation a : annotations) {
                        if (a.annotationType().equals(Observes.class)) {
                            if (!observerMethods.containsKey(type)) {
                                observerMethods.put(type, new ArrayList<Method>());
                            }
                            if (!observerMethods.get(type).contains(m)) {
                                observerMethods.get(type).add(m);
                            }
                        }
                    }
                }
            }
            for (Method m : toRegister.getDeclaredMethods()) {
                if (m.getParameterTypes().length == 1) {
                    if (m.getParameterTypes().length == 1) {
                        Class<?> type = m.getParameterTypes()[0];
                        Annotation[] annotations = m.getParameterAnnotations()[0];
                        for (Annotation a : annotations) {
                            if (a.annotationType().equals(Observes.class)) {
                                if (!observerMethods.containsKey(type)) {
                                    observerMethods.put(type, new ArrayList<Method>());
                                }
                                if (!observerMethods.get(type).contains(m)) {
                                    observerMethods.get(type).add(m);
                                }
                            }
                        }
                    }
                }
            }
            MethodEventListener methodListener = new MethodEventListener(toRegister, injector, observerMethods);
            for (Class type : observerMethods.keySet()) {
                if (!listeners.containsKey(type)) {
                    listeners.put(type, new ArrayList<EventListener>());
                }
                listeners.get(type).add(methodListener);
            }
        }
    }

    @Override
    public <T> void fireAsyncEvent(T evt) {
        ArrayList<EventListener> list = listeners.get(evt.getClass());
        if (list != null) {
            if (!list.isEmpty()) {
                EventBroadcastExecution task = new EventBroadcastExecution(evt, listeners.get(evt.getClass()));
                exec.execute(task);
            }
        }
    }

    @Override
    public <T> void fireEvent(T evt) {
        ArrayList<EventListener> list = listeners.get(evt.getClass());
        if (list != null) {
            if (!list.isEmpty()) {
                for (EventListener listener : list) {
                    listener.onEvent(evt);
                }
            }
        }
    }

    public ExecutorService getExec() {
        return exec;
    }

    public boolean isTerminated() {
        return exec.isTerminated();
    }

    private class MethodEventListener implements EventListener {

        private final Class<?> listenerClass;
        private final DSInjector injector;
        private final Map<Class, List<Method>> observerMethods;

        public MethodEventListener(Class<?> listenerClass, DSInjector injector, Map<Class, List<Method>> observerMethods) {
            this.listenerClass = listenerClass;
            this.injector = injector;
            this.observerMethods = observerMethods;
//            for (Method m : listenerClass.getMethods()) {
//                if (m.getParameterTypes().length == 1) {
//                    Class<?> type = m.getParameterTypes()[0];
//                    Annotation[] annotations = m.getParameterAnnotations()[0];
//                    for (Annotation a : annotations) {
//                        if (a.annotationType().equals(Observes.class)) {
//                            if (!observerMethods.containsKey(type)) {
//                                observerMethods.put(type, new ArrayList<Method>());
//                            }
//                            if (!observerMethods.get(type).contains(m)) {
//                                observerMethods.get(type).add(m);
//                            }
//                        }
//                    }
//                }
//            }
//            for (Method m : listenerClass.getDeclaredMethods()) {
//                if (m.getParameterTypes().length == 1) {
//                    if (m.getParameterTypes().length == 1) {
//                        Class<?> type = m.getParameterTypes()[0];
//                        Annotation[] annotations = m.getParameterAnnotations()[0];
//                        for (Annotation a : annotations) {
//                            if (a.annotationType().equals(Observes.class)) {
//                                if (!observerMethods.containsKey(type)) {
//                                    observerMethods.put(type, new ArrayList<Method>());
//                                }
//                                if (!observerMethods.get(type).contains(m)) {
//                                    observerMethods.get(type).add(m);
//                                }
//                            }
//                        }
//                    }
//                }
//            }
        }

        @Override
        public void onEvent(Object evt) {
            Object instance = injector.getInstance(listenerClass);
            List<Method> observers = observerMethods.get(evt.getClass());
            for (Method m : observers) {
                try {
                    m.invoke(instance, new Object[]{evt});
                } catch (Exception ex) {
                    logger.log(Level.SEVERE, "Can't call Observer method : ", ex);
                } 
            }
        }
    }

    private class EventListenerDecorator implements EventListener {

        private final Class<? extends EventListener> listenerClass;
        private final DSInjector injector;

        public EventListenerDecorator(Class<? extends EventListener> listenerClass, DSInjector injector) {
            this.listenerClass = listenerClass;
            this.injector = injector;
        }

        @Override
        public void onEvent(Object evt) {
            injector.getInstance(listenerClass).onEvent(evt);
        }
    }
}
