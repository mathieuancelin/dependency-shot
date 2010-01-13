package cx.ath.mancel01.dependencyshot.test.basic;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class BasicFluentBinder extends Binder {

    @Override
    public void configureBindings() {
        fbind(BasicService.class).to(BasicServiceImpl.class);
        fbind(BasicClient.class);
    }
}
