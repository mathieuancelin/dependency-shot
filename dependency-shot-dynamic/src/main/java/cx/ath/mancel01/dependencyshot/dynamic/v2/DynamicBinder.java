/*
 *  Copyright 2010 mathieu.
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

package cx.ath.mancel01.dependencyshot.dynamic.v2;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieu
 */
public abstract class DynamicBinder extends Binder {

    private static final Logger logger = Logger.getLogger(DynamicBinder.class.getName());

    public void bindDynamically(Class<?> from) {
        try {
            bind(from);
            Field fTo = Binder.class.getDeclaredField("to");
            fTo.setAccessible(true);
            fTo.set(this, DynamicImplementation.class);
            fTo.setAccessible(true);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "Unable to bind dynamically the class " + from.getName(), ex);
        }
    }
}
