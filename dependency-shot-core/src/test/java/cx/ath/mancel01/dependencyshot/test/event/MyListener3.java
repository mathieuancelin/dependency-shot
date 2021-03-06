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

import cx.ath.mancel01.dependencyshot.api.event.EventListener;
import java.util.concurrent.CountDownLatch;
import javax.inject.Singleton;

/**
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public class MyListener3 implements EventListener<MyEvent3> {

    private int calls = 0;

    private CountDownLatch latch = new CountDownLatch(EventTest.NBR);

    @Override
    public void onEvent(MyEvent3 evt) {
       calls++;
       latch.countDown();
    }

    public int getCalls() {
        return calls;
    }

    public CountDownLatch getLatch() {
        return latch;
    }

}
