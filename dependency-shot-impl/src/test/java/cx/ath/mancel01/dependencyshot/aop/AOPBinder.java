package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class AOPBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(AOPService.class).to(AOPServiceImpl.class);
    }
}
