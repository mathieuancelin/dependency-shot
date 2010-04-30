package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class LifecycleFluentBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(LifecycleService.class).to(LifecycleServiceImpl.class);
        /**
         * Not needed since single bindings are automatically detected.
         * bind(LifecycleClient.class);
         **/
    }
}
