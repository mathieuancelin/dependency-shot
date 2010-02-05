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
        bind(BasicService.class).to(BasicServiceImpl.class);
        /**
         * Not needed since single bindings are automatically detected.
         * bind(BasicClient.class);
         **/
    }
}
