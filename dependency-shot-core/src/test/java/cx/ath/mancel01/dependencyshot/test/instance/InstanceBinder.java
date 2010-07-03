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

package cx.ath.mancel01.dependencyshot.test.instance;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import javax.inject.Provider;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceBinder extends Binder {

    public static final String MY_MODULE = "MyModule";
    public static final String MY_OTHER_MODULE = "MyOtherModule";
    public static final String YAM = "YetAnotherModule";

    @Override
    public void configureBindings() {
        bind(String.class).annotatedWith(MyModule.class).providedBy(new Provider(){
            @Override
            public Object get() {
                return MY_MODULE;
            }
        });
        bind(String.class).annotatedWith(MyOtherModule.class).providedBy(new Provider(){
            @Override
            public Object get() {
                return MY_OTHER_MODULE;
            }
        });
        bind(String.class).named("yam").providedBy(new Provider(){
            @Override
            public Object get() {
                return YAM;
            }
        });
        
        bind(InstanceModule.class);
        bind(InstanceModule2.class);
        bind(InstanceModule3.class);
    }
}
