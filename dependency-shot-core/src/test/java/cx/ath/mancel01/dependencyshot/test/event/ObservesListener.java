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

import cx.ath.mancel01.dependencyshot.api.event.Observes;
import java.util.concurrent.CountDownLatch;
import javax.inject.Singleton;

/**
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public class ObservesListener {

    private int callsEvent1 = 0;
    private int callsEvent2 = 0;

    private CountDownLatch latchEvent1 = new CountDownLatch(EventTest.NBR);
    private CountDownLatch latchEvent2 = new CountDownLatch(EventTest.NBR);

    public int getCallsEvent1() {
        return callsEvent1;
    }

    public int getCallsEvent2() {
        return callsEvent2;
    }

    public CountDownLatch getLatchEvent2() {
        return latchEvent2;
    }

    public CountDownLatch getLatchEvent1() {
        return latchEvent1;
    }

    public void listenToEvent1(@Observes Event1 event) {
        callsEvent1++;
        latchEvent1.countDown();
    }

    public void listenToEvent2(@Observes Event2 event) {
        callsEvent2++;
        latchEvent2.countDown();
    }
}
