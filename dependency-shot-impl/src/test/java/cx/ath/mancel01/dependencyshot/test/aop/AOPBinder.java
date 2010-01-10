package cx.ath.mancel01.dependencyshot.test.aop;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class AOPBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(AOPService.class, AOPServiceImpl.class);
        bind(AOPClient.class);
        bind(AOPMethodInterceptor.class);
        bind(AOPInjectedInInterceptor.class);
    }
}
