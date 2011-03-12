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

/**
 *
 * @author Mathieu ANCELIN
 */
public class InjectionStoppedEvent {

    public long timestamp;
    private Class beanType;
    private Object beanInstance;

    public InjectionStoppedEvent() {
        timestamp = System.currentTimeMillis();
    }

    public Object getBeanInstance() {
        return beanInstance;
    }

    public void setBeanInstance(Object beanInstance) {
        this.beanInstance = beanInstance;
    }

    public Class getBeanType() {
        return beanType;
    }

    public void setBeanType(Class beanType) {
        this.beanType = beanType;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
