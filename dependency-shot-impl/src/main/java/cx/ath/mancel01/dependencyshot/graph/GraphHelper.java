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

package cx.ath.mancel01.dependencyshot.graph;

import cx.ath.mancel01.dependencyshot.api.IBinder;
import cx.ath.mancel01.dependencyshot.api.IBinding;
import java.util.Vector;

/**
 *
 * @author mathieuancelin
 */
public class GraphHelper {

    /**
     * The unique instance of the class
     **/
    private static GraphHelper INSTANCE = null;

    /**
     * The private constructor of the singleton
     **/
    private GraphHelper() {

    }

    /**
     * The accessor for the unique instance of the singleton
     **/
    public static synchronized GraphHelper getInstance() {
        if ( INSTANCE == null ) {
            INSTANCE = new GraphHelper();
        }
        return INSTANCE;
    }

    public IBinding findBinding(Class clazz, Vector<IBinder> binders){
        for(IBinder binder : binders){
            for(IBinding binding : binder.getBindings().values()){
                if(binding.getGeneric().equals(clazz)){
                    return binding;
                }
            }
        }
        return null;
    }
}
