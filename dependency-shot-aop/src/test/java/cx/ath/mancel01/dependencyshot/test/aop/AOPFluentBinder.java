package cx.ath.mancel01.dependencyshot.test.aop;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class AOPFluentBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(AOPService.class).to(AOPServiceImpl.class);
        /**
         * Not needed since single bindings are automatically detected.
        bind(AOPClient.class);
        bind(AOPMethodInterceptor.class);
        bind(AOPInjectedInInterceptor.class);
        **/
    }
}
