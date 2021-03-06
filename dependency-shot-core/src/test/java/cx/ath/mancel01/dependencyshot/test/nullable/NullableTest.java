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

package cx.ath.mancel01.dependencyshot.test.nullable;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.exceptions.ExceptionManagedException;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author Mathieu ANCELIN
 */
public class NullableTest {

    @Test
    public void testNullable() {
        DSInjector injector = DependencyShot.getInjector(new NullableBinder());
        NullableClient client = injector.getInstance(NullableClient.class);
        Assert.assertNull(client.getService1());
        Assert.assertNull(client.getService2());
        Assert.assertNull(client.getService3());
    }

    @Test(expected=ExceptionManagedException.class)
    public void testNotNullable() {
        DSInjector injector = DependencyShot.getInjector(new NullableBinder());
        NotNullableClient client = injector.getInstance(NotNullableClient.class);
        Assert.assertNull(client);
    }
    
}
