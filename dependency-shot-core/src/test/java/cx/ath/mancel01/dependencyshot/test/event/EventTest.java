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
import cx.ath.mancel01.dependencyshot.api.event.Event;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import java.util.concurrent.TimeUnit;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author mathieuancelin
 */
public class EventTest {

    public static final int NBR = 40;

    @Test
    public void broadcast() throws Exception {
        DSInjector injector = DependencyShot.getInjector(new Binder() {

            @Override
            public void configureBindings() {
                bind(MyEvent.class);
                bind(MyEvent2.class);
                bind(MyEvent3.class);
                registerEventListener(MyListener.class);
                registerEventListener(MyListener2.class);
                registerEventListener(MyListener3.class);
            }

        });
        MyEvent event = injector.getInstance(MyEvent.class);
        MyEvent2 event2 = injector.getInstance(MyEvent2.class);
        MyEvent3 event3 = injector.getInstance(MyEvent3.class);
        MyListener listener = injector.getInstance(MyListener.class);
        MyListener2 listener2 = injector.getInstance(MyListener2.class);
        MyListener3 listener3 = injector.getInstance(MyListener3.class);
        for(int i = 0; i < NBR; i++)
            event.fire();
        for(int i = 0; i < NBR; i++)
            event2.fire();
        for(int i = 0; i < NBR; i++)
            event3.fire();

        Assert.assertTrue(listener.getLatch().await(10, TimeUnit.SECONDS));
        Assert.assertTrue(listener2.getLatch().await(10, TimeUnit.SECONDS));
        Assert.assertTrue(listener3.getLatch().await(10, TimeUnit.SECONDS));

        System.out.println("listener 1 : " + listener.getCalls() + " events received ...");
        System.out.println("listener 2 : " + listener2.getCalls() + " events received ...");
        System.out.println("listener 3 : " + listener3.getCalls() + " events received ...");
        Assert.assertTrue(listener.getCalls() == NBR);
        Assert.assertTrue(listener2.getCalls() == NBR);
        Assert.assertTrue(listener3.getCalls() == NBR);
    }

    @Test
    public void broadcastEvent() throws Exception {
        DSInjector injector = DependencyShot.getInjector(new Binder() {

            @Override
            public void configureBindings() {
                bind(MyEvent.class);
                registerEventListener(CustomEventListener.class);
            }

        });
        Event<CustomEvent> event = injector.getInstance(Event.class);
        CustomEventListener listener = injector.getInstance(CustomEventListener.class);
        for(int i = 0; i < NBR; i++)
            event.fire(new CustomEvent());
        
        Assert.assertTrue(listener.getLatch().await(10, TimeUnit.SECONDS));

        System.out.println("listener 1 : " + listener.getCalls() + " events received ...");
        Assert.assertTrue(listener.getCalls() == NBR);
    }

    @Test
    public void broadcastEventToObservers() throws Exception {
        DSInjector injector = DependencyShot.getInjector(new Binder() {

            @Override
            public void configureBindings() {
                bind(Event1.class);
                bind(Event2.class);
                registerEventListener(ObservesListener.class);
            }

        });
        Event<Event1> event1 = injector.getInstance(Event.class);
        Event<Event2> event2 = injector.getInstance(Event.class);
        ObservesListener listener = injector.getInstance(ObservesListener.class);
        for(int i = 0; i < NBR; i++) {
            event1.fire(new Event1());
        }
        for(int i = 0; i < NBR; i++) {
            event2.fire(new Event2());
        }

        Assert.assertTrue(listener.getLatchEvent1().await(10, TimeUnit.SECONDS));
        Assert.assertTrue(listener.getLatchEvent2().await(10, TimeUnit.SECONDS));

        System.out.println("listener 1 events 1 : " + listener.getCallsEvent1() + " events received ...");
        System.out.println("listener 1 events 2 : " + listener.getCallsEvent2() + " events received ...");
        Assert.assertTrue(listener.getCallsEvent1() == NBR);
        Assert.assertTrue(listener.getCallsEvent2() == NBR);
    }
}