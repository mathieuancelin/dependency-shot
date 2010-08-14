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

package cx.ath.mancel01.dependencyshot.test.circular;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 *
 * @author mathieuancelin
 */
@Singleton
public class SingletonConstructorA implements SingletonInterfaceA {

    private SingletonInterfaceB b;

    @Inject
    public SingletonConstructorA(SingletonInterfaceB b) {
        this.b = b;
    }

    @Override
    public String getValue() {
       return "SingletonConstructorA";
    }

    @Override
    public SingletonInterfaceB getB() {
        return b;
    }

}
