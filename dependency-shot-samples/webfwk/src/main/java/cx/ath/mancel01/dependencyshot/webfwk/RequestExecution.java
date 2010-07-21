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

package cx.ath.mancel01.dependencyshot.webfwk;

import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.webfwk.FrontalServlet.HttpAction;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author mathieuancelin
 */
public class RequestExecution implements Runnable {

    private HttpAction action;
    
    private HttpServletRequest request;

    private HttpServletResponse response;

    private DSInjector injector;

    public HttpAction getAction() {
        return action;
    }

    public void setAction(HttpAction action) {
        this.action = action;
    }

    public HttpServletRequest getRequest() {
        return request;
    }

    public void setRequest(HttpServletRequest request) {
        this.request = request;
    }

    public HttpServletResponse getResponse() {
        return response;
    }

    public void setResponse(HttpServletResponse response) {
        this.response = response;
    }

    public void setInjector(DSInjector injector) {
        this.injector = injector;
    }

    public DSInjector getInjector() {
        return injector;
    }

    @Override
    public void run() {
        // match request with route
        // find controller for route
        Object controller = injector.getInstance(null); // isntanciate the controller
        // perform render based on http action
    }
}
