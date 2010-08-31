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

import cx.ath.mancel01.dependencyshot.api.event.EventListener;
import cx.ath.mancel01.dependencyshot.api.event.Event;
import cx.ath.mancel01.dependencyshot.api.event.EventManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
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
    
    public EventManagerImpl() {
        listeners = new HashMap<Class<?>, ArrayList<EventListener>>();
    }

    public void registerListeners(Collection<EventListener> listeners) {
        for (EventListener evt : listeners) {
            registerListener(evt);
        }
    }

    public void registerListener(EventListener toRegister) {
        Type[] interfaces = toRegister.getClass().getGenericInterfaces();
        Class<? extends Event> type = null;
        for(Type interf : interfaces) {
            if (EventListener.class.isAssignableFrom((Class)((ParameterizedType)interf).getRawType())) {
                type = (Class<? extends Event>)((ParameterizedType) interf).getActualTypeArguments()[0];
            }
        }
        if (type != null) {
            if (!listeners.containsKey(type)) {
                listeners.put(type, new ArrayList<EventListener>());
            }
            listeners.get(type).add(toRegister);
        }
    }

    @Override
    public <T> void fireEvent(T evt) {
        ArrayList<EventListener> list = listeners.get(evt.getClass());
        if (list != null) {
            if (!list.isEmpty()) {
                EventBroadcastExecution task = new EventBroadcastExecution(evt, listeners.get(evt.getClass()));
                exec.execute(task);
            }
        }
    }

    public ExecutorService getExec() {
        return exec;
    }

}
