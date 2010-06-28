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

package cx.ath.mancel01.dependencyshot.dsl.tck;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.atinject.tck.Tck;
import org.atinject.tck.auto.Car;
import org.atinject.tck.auto.Convertible;
import org.atinject.tck.auto.Tire;
import org.atinject.tck.auto.accessories.SpareTire;

/**
 * The test suite with TCK tests.
 * 
 * @author Mathieu ANCELIN
 */
public class TCKFluentStaticTest extends TestSuite {
    
    public static Test suite() {
        DSInjector injector = DependencyShot.getSpecificConfigurator()
                .getInjector("src/main/resources/tck.groovy");
        injector.injectStatics(Convertible.class);
        injector.injectStatics(Tire.class);
        injector.injectStatics(SpareTire.class);
        Car car = injector.getInstance(Car.class);
        return Tck.testsFor(car, true, true);
    }
}
