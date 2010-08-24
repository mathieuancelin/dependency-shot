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

package cx.ath.mancel01.dependencyshot.test.event;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.event.EventManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author mathieuancelin
 */
public class EventTest {

    @Test
    public void broadcast() {
        DSInjector injector = DependencyShot.getInjector();
        EventManager manager = injector.getInstance(EventManager.class);
        MyEvent event = injector.getInstance(MyEvent.class);
        MyEvent2 event2 = injector.getInstance(MyEvent2.class);
        MyEvent3 event3 = injector.getInstance(MyEvent3.class);
        MyListener listener = new MyListener();
        MyListener2 listener2 = new MyListener2();
        MyListener3 listener3 = new MyListener3();
        manager.registerListener(listener);
        manager.registerListener(listener2);
        manager.registerListener(listener3);
        for(int i = 0; i < 20; i++)
            event.fire();
        for(int i = 0; i < 20; i++)
            event2.fire();
        for(int i = 0; i < 20; i++)
            event3.fire();
        try {
            Thread.sleep(200);
        } catch (InterruptedException ex) {
            Logger.getLogger(EventTest.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
        Assert.assertTrue(listener.getCalls() == 20);
        Assert.assertTrue(listener2.getCalls() == 20);
        Assert.assertTrue(listener3.getCalls() == 20);
    }

}