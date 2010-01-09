package cx.ath.mancel01.dependencyshot.test.basic;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class BasicBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(BasicService.class, BasicServiceImpl.class);
        bind(BasicClient.class);
    }
}
