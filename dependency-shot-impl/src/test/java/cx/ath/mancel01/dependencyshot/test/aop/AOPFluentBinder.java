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
        fbind(AOPService.class).to(AOPServiceImpl.class);
        fbind(AOPClient.class);
        fbind(AOPMethodInterceptor.class);
        fbind(AOPInjectedInInterceptor.class);
    }
}
