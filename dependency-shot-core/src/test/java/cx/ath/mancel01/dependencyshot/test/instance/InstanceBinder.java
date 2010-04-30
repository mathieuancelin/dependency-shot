package cx.ath.mancel01.dependencyshot.test.instance;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import javax.inject.Provider;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceBinder extends Binder {

    public static final String MY_MODULE = "MyModule";
    public static final String MY_OTHER_MODULE = "MyOtherModule";
    public static final String YAM = "YetAnotherModule";

    @Override
    public void configureBindings() {
        bind(String.class).annotedWith(MyModule.class).providedBy(new Provider(){
            @Override
            public Object get() {
                return MY_MODULE;
            }
        });
        bind(String.class).annotedWith(MyOtherModule.class).providedBy(new Provider(){
            @Override
            public Object get() {
                return MY_OTHER_MODULE;
            }
        });
        bind(String.class).named("yam").providedBy(new Provider(){
            @Override
            public Object get() {
                return YAM;
            }
        });
        
        bind(InstanceModule.class);
        bind(InstanceModule2.class);
        bind(InstanceModule3.class);
    }
}
