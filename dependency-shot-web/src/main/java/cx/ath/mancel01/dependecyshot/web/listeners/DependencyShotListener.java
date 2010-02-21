/*
 *  Copyright 2010 Mathieu ANCELIN.
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

package cx.ath.mancel01.dependecyshot.web.listeners;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Web application lifecycle listener to bootstrap the framework.
 * 
 * @author Mathieu ANCELIN
 */
public class DependencyShotListener implements ServletContextListener, ServletContextAttributeListener, HttpSessionListener, HttpSessionAttributeListener{
    /**
     * Name of the injector in the servlet context.
     */
    public static final String INJECTOR_NAME = "DSInjector";
    /**
     * Name of the default property to get the config binder.
     */
    public static final String INJECTOR_CONFIG_CLASS = "DependencyShotConfig";
    /**
     * Binder of the webapp.
     */
    private Binder binder = null;
    /**
     * Injector of the webapp.
     */
    private DSInjector injector = null;
    /**
     * {@inheritDoc}
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        if (binder != null) {
            injector = DependencyShot.getInjector(binder);
        } else {
            try {
            binder = (Binder) DependencyShotListener.class
                    .getClassLoader()
                    .loadClass(
                        context.getInitParameter(INJECTOR_CONFIG_CLASS)
                    )
                    .newInstance();
            } catch (Exception ex) {
                Logger.getLogger(DependencyShotListener.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (binder != null) {
                injector = DependencyShot.getInjector(binder);
            } else {
                injector = DependencyShot.getInjector();
            }
        }
        context.setAttribute(INJECTOR_NAME, injector);
    }
    /**
     * {@inheritDoc}
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeAdded(ServletContextAttributeEvent scab) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeRemoved(ServletContextAttributeEvent scab) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeReplaced(ServletContextAttributeEvent scab) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionCreated(HttpSessionEvent se) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent se) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent se) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent se) {}
    /**
     * {@inheritDoc}
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent se) {}
}
