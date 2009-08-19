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

import cx.ath.mancel01.dependencyshot.api.IBinding;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mathieuancelin
 */
public class Binding implements IBinding{
    
    public enum SCOPE {
        SINGLETON,
        NORMAL
    };

    private Class generic;

    private Class specific;

    private Vector<Object> specificInstances;

    private Object uniqueInstance;

    private SCOPE scope;

    public Class getGeneric() {
        return generic;
    }

    public void setGeneric(Class generic) {
        this.generic = generic;
    }

    public SCOPE getScope() {
        return scope;
    }

    public void setScope(SCOPE scope) {
        this.scope = scope;
    }

    public Class getSpecific() {
        return specific;
    }

    public void setSpecific(Class specific) {
        this.specific = specific;
    }

    public Vector<Object> getSpecificInstances() {
        return specificInstances;
    }

    public void setSpecificInstances(Vector<Object> specificInstances) {
        this.specificInstances = specificInstances;
    }

    public void addSpecificInstance(Object obj){
        this.specificInstances.add(obj);
    }

    public void remSpecificInstance(Object obj){
        this.specificInstances.remove(obj);
    }

    public Object getUniqueInstance() {
        return uniqueInstance;
    }

    public void setUniqueInstance(Object uniqueInstance) {
        this.uniqueInstance = uniqueInstance;
    }

    @Override
    public Object getSpecificInstance(){
        try {
            // verify rules (singleton etc...)
            return this.specific.newInstance(); // check if injectable ?
        } catch (Exception ex) {
            Logger.getLogger(Binding.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } 
    }

}
