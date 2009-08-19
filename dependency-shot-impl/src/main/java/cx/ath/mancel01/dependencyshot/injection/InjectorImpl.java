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
import cx.ath.mancel01.dependencyshot.api.Injector;
import java.util.Vector;

/**
 *
 * @author mathieuancelin
 */
public class InjectorImpl implements Injector {

    private Vector<IBinder> binders;

    public InjectorImpl() {
        this.binders = new Vector();
    }

    @Override
    public Object getObjectInstance(Class clazz) {
        return AnnotationsProcessor.getInstance().processClassAnnotations(clazz, binders);
    }

    @Override
    public Object injectInstance(Object obj){
        return AnnotationsProcessor.getInstance().processInstanceAnnotations(obj, binders);
    }

    @Override
    public Object injectMembers(Object obj){
        return AnnotationsProcessor.getInstance().processInstanceMethodAnnotations(obj, binders);
    }

    @Override
    public Object injectFiels(Object obj){
        return AnnotationsProcessor.getInstance().processInstanceFieldsAnnotations(obj, binders);
    }

    public void configureBinders(){
        for(IBinder binder : binders){
            binder.configureBindings();
        }
    }

    public void addBinder(IBinder binder){
        this.binders.add(binder);
    }

}
