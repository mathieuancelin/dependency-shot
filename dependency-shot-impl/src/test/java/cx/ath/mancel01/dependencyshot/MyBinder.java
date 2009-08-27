package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class MyBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(Service.class).to(ServiceImpl.class);
    }
}
