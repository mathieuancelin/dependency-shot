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

import cx.ath.mancel01.dependencyshot.api.Nullable;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of a service.
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceModule3  implements Module {

    @Inject @Named("yam")
    private String name;

    @Inject @Named("null") @Nullable
    private String nonNullString;

    @Override
    public void start() {
        System.out.println("Starting module : " + name);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getNullString() {
        return nonNullString;
    }
}
