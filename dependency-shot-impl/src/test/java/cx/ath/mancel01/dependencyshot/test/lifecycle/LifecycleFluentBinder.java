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
        fbind(LifecycleService.class).to(LifecycleServiceImpl.class);
        fbind(LifecycleClient.class);
    }
}
