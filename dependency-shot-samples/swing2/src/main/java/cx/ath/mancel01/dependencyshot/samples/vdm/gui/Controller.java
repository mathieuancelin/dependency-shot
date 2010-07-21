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

package cx.ath.mancel01.dependencyshot.samples.vdm.gui;

import cx.ath.mancel01.dependencyshot.samples.vdm.model.Vdm;
import javax.inject.Singleton;

/**
 * Interface for an app controller.
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public interface Controller {
    /**
     * Get the current displayed model.
     *
     * @return the current displayed model.
     */
    Vdm getModel();
    /**
     * Call an update action on the model
     */
    void updateModel();
}
