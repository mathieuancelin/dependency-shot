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

package cx.ath.mancel01.dependencyshot.configurator.tests;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.configurator.AnnotationsConfigurator;
import org.junit.Test;

/**
 *
 * @author Mathieu ANCELIN
 */
public class ScanningTest {

    @Test
    public void testScanning() {
        final DSInjector injector = DependencyShot
                .configurator(AnnotationsConfigurator.class)
                .withPackagePrefix("com.exemple")
                .showBindings(true)
                .getInjector();
    }

    @Test
    public void testScanningWithoutPrefix() {
        final DSInjector injector = DependencyShot
                .configurator(AnnotationsConfigurator.class)
                .showBindings(true)
                .getInjector();
    }
}
