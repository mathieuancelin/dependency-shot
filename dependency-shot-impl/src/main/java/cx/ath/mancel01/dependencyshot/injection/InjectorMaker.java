/*
 *  Copyright 2009 mathieuancelin.
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

package cx.ath.mancel01.dependencyshot.injection;

import cx.ath.mancel01.dependencyshot.api.IBinder;
import cx.ath.mancel01.dependencyshot.xml.XMLBindingsHelper;

/**
 *
 * @author mathieuancelin
 */
public class InjectorMaker{
    // TODO implements auto binding
    public static InjectorImpl makeInjector(Iterable<? extends IBinder> binders) {
        InjectorImpl injector = new InjectorImpl();
        for(IBinder binder : binders){
            injector.addBinder(binder);
        }
        injector.configureBinders();
        return injector;
    }

    public static InjectorImpl makeInjector(IBinder binder) {
        InjectorImpl injector = new InjectorImpl();
        injector.addBinder(binder);
        injector.configureBinders();
        return injector;
    }
    
    // TODO implements auto binding
    public static InjectorImpl makeInjector() {
        return null;
    }

    // TODO implements xml binding
    public static InjectorImpl makeInjector(String configPath) {
        XMLBindingsHelper.getInstance();
        return null;
    }


}
