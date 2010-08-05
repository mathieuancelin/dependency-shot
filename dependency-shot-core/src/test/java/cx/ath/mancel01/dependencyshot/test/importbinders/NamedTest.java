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

package cx.ath.mancel01.dependencyshot.test.importbinders;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;
/**
 *
 * @author mathieuancelin
 */
public class NamedTest {

    @Test
    public void testMultipleNamed() {
        DSInjector injector = DependencyShot.getInjector(new Binder() {

            @Override
            public void configureBindings() {
                bind(Service1.class).to(Service1Impl.class);
                bind(Service2.class).to(Service2Impl.class);
                importBindingsFrom(new NamedBinder());
            }

        });
        InjectedClass injected = injector.getInstance(InjectedClass.class);
        assertTrue(injected.getService1().getClass().getSimpleName().equals(Service1Impl.class.getSimpleName()));
        assertTrue(injected.getService2().getClass().getSimpleName().equals(Service2Impl.class.getSimpleName()));
        assertTrue(injected.getService11().getClass().getSimpleName().equals(Service1Impl.class.getSimpleName()));
        assertTrue(injected.getService22().getClass().getSimpleName().equals(Service2Impl.class.getSimpleName()));
    }

    @Test
    public void testMultipleImports() {
        DSInjector injector = DependencyShot.getInjector(new Binder() {

            @Override
            public void configureBindings() {
                importBindingsFrom(new UnnamedBinder());
                importBindingsFrom(new NamedBinder());
            }

        });
        InjectedClass injected = injector.getInstance(InjectedClass.class);
        assertTrue(injected.getService1().getClass().getSimpleName().equals(Service1Impl.class.getSimpleName()));
        assertTrue(injected.getService2().getClass().getSimpleName().equals(Service2Impl.class.getSimpleName()));
        assertTrue(injected.getService11().getClass().getSimpleName().equals(Service1Impl.class.getSimpleName()));
        assertTrue(injected.getService22().getClass().getSimpleName().equals(Service2Impl.class.getSimpleName()));
    }
}