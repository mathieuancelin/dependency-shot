package cx.ath.mancel01.dependencyshot.test.instance;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class EasyInstanceBinder extends Binder {

    public static final String MY_MODULE = "MyModule";
    public static final String MY_OTHER_MODULE = "MyOtherModule";
    public static final String YAM = "YetAnotherModule";

    @Override
    public void configureBindings() {
        fbind(String.class).annotedWith(MyModule.class).toInstance(MY_MODULE);
        fbind(String.class).annotedWith(MyOtherModule.class).toInstance(MY_OTHER_MODULE);
        fbind(String.class).named("yam").toInstance(YAM);
    }
}
