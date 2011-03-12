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

package cx.ath.mancel01.dependencyshot.test.exception;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.exceptions.ExceptionManagedException;
import javax.inject.Inject;
import org.junit.Test;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class ExceptionTest {

    @Test(expected=ExceptionManagedException.class)
    public void testExceptionManager() {
        DSInjector injector = DependencyShot.getInjector();
        OtherClass obj = injector.getInstance(OtherClass.class);
    }

    public static class OtherClass {
        
        @Inject private MyClass clazz;

    }

    public static class MyClass {

        private String value;

        public MyClass(String value) {
            this.value = value;
        }
    }
}
