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

package cx.ath.mancel01.dependencyshot.event;

import cx.ath.mancel01.dependencyshot.api.event.EventManager;
import cx.ath.mancel01.dependencyshot.exceptions.ExceptionManager;
import java.util.UUID;
import javax.inject.Inject;

/**
 * Event class.
 *
 * @author mathieuancelin
 */
public abstract class AutoEvent {

    private String id;

    private long timestamp;

    @Inject
    private EventManager manager;

    public AutoEvent() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void fire() {
        if (manager == null) {
            ExceptionManager.makeException("You should inject the event in order to call fire on it.").throwManaged();
            throw new RuntimeException("You should inject the event in order to call fire on it.");
        }
        manager.fireEvent(this);
    }

    public void fireAsync() {
        if (manager == null) {
            ExceptionManager.makeException("You should inject the event in order to call fire on it.").throwManaged();
            throw new RuntimeException("You should inject the event in order to call fire on it.");
        }
        manager.fireAsyncEvent(this);
    }
}
