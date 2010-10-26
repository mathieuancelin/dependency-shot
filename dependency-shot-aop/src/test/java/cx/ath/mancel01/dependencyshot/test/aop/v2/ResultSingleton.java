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

package cx.ath.mancel01.dependencyshot.test.aop.v2;

import javax.inject.Singleton;

/**
 *
 * @author Mathieu ANCELIN
 */
@Singleton
public class ResultSingleton {

    private int beforeCall = 0;

    private int afterCall = 0;

    private int beforeHello = 0;

    private int afterHello = 0;

    private int beforeSomething = 0;

    private int afterSomething = 0;

    private int beforeGoodbye = 0;

    private int afterGoodbye = 0;
    
    public void incrementBeforeCall() {
        this.beforeCall++;
    }

    public void incrementAfterCall() {
        this.afterCall++;
    }

    public void incrementAfterGoodbye() {
        this.afterGoodbye++;
    }

    public void incrementAfterHello() {
        this.afterHello++;
    }

    public void incrementAfterSomething() {
        this.afterSomething++;
    }

    public void incrementBeforeGoodbye() {
        this.beforeGoodbye++;
    }

    public void incrementBeforeHello() {
        this.beforeHello++;
    }

    public void incrementBeforeSomething() {
        this.beforeSomething++;
    }
    
    public int getAfterCall() {
        return afterCall;
    }

    public int getAfterGoodbye() {
        return afterGoodbye;
    }

    public int getAfterHello() {
        return afterHello;
    }

    public int getAfterSomething() {
        return afterSomething;
    }

    public int getBeforeCall() {
        return beforeCall;
    }

    public int getBeforeGoodbye() {
        return beforeGoodbye;
    }

    public int getBeforeHello() {
        return beforeHello;
    }

    public int getBeforeSomething() {
        return beforeSomething;
    }

    public void reset(){
        this.afterGoodbye = 0;
        this.afterHello = 0;
        this.afterSomething = 0;
        this.beforeGoodbye = 0;
        this.beforeHello = 0;
        this.beforeSomething = 0;
        this.beforeCall = 0;
        this.afterCall = 0;
    }
}
