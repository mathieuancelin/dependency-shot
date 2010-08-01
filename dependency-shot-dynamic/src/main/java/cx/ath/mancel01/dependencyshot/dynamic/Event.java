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

/**
 * Event for dynamic service notifications.
 *
 * @author Mathieu ANCELIN
 */
public class Event {

    private static long idCounter = 0;

    private long id;

    private Object value;

    private DynamicService service;

    private Type type = Type.IMPL_CHANGED;

    public Event(DynamicService service) {
        this.id = idCounter++;
        this.value = null;
        this.service = service;
    }

    public Event(DynamicService service, Object value) {
        this.id = idCounter++;
        this.value = value;
        this.service = service;
    }

    public final long getId() {
        return id;
    }

    public final DynamicService getService() {
        return service;
    }

    public final Object getValue() {
        return value;
    }

    public final void setValue(Object value) {
        this.value = value;
    }

    public final Type getType() {
        return type;
    }
}
