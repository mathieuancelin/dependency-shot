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

package cx.ath.mancel01.dependencyshot.test.aop;

import java.util.Vector;

/**
 *
 * @author Mathieu ANCELIN
 */
public class AOPInterceptionResult {

    private static AOPInterceptionResult instance = null;

    private Vector<String> preClass = new Vector<String>();

    private Vector<String> postClass = new Vector<String>();

    private Vector<String> preMethod = new Vector<String>();

    private Vector<String> postMethod = new Vector<String>();


    private AOPInterceptionResult() {

    }

    public static AOPInterceptionResult getInstance() {
        if(instance == null)
            instance = new AOPInterceptionResult();
        return instance;
    }

    public Vector<String> getPostClass() {
        return postClass;
    }

    public Vector<String> getPostMethod() {
        return postMethod;
    }

    public Vector<String> getPreClass() {
        return preClass;
    }

    public Vector<String> getPreMethod() {
        return preMethod;
    }

    public void reset() {
        preClass = new Vector<String>();

        postClass = new Vector<String>();

        preMethod = new Vector<String>();

        postMethod = new Vector<String>();
    }
}
