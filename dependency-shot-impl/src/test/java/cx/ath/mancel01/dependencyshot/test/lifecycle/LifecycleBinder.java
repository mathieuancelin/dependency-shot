package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class LifecycleBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(LifecycleService.class, LifecycleServiceImpl.class);
        bind(LifecycleClient.class);
    }
}
