/*
 *  Copyright 2009 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependencyshot.test;

import org.junit.Test;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import cx.ath.mancel01.dependencyshot.test.junit.BinderSource;
import cx.ath.mancel01.dependencyshot.test.junit.DependencyShot;
import javax.inject.Inject;
import junit.framework.TestResult;
import org.atinject.tck.Tck;
import org.atinject.tck.auto.Car;
import org.junit.runner.RunWith;
import static junit.framework.Assert.assertTrue;

/**
 * The test suite with TCK tests.
 * 
 * @author Mathieu ANCELIN
 */
@RunWith(DependencyShot.class)
public class TCKFluentTest {

    @BinderSource
    public static Binder getBinder() {
        return new TCKFluentBinder();
    }

    @Inject
    private Car car;

    @Test
    public void passTCK() {
  	junit.framework.Test test = Tck.testsFor(car, false, true);
        TestResult result = new TestResult();
        result.startTest(test);
        assertTrue(result.wasSuccessful());
    }
}
