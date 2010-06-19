/*
 *  Copyright 2009-2010 Mathieu ANCELIN
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

package cx.ath.mancel01.dependencyshot.exceptions;

/**
 * A common exception of dependency-shot.
 * @author Mathieu ANCELIN
 */
public class DSCyclicDependencyDetectedException extends RuntimeException {
    /**
     * Constructor.
     * @param message the error message.
     */
    public DSCyclicDependencyDetectedException(final String message) {
        super(message);
    }
    /**
     * Constructor.
     *
     * @param message the error message.
     */
    public DSCyclicDependencyDetectedException(final Exception message) {
        super(message);
    }
    /**
     * Constructor.
     *
     * @param message the error message.
     * @param ex the exceptions to launch.
     */
    public DSCyclicDependencyDetectedException(final String message, final Exception ex) {
        super(message, ex);
    }
}

