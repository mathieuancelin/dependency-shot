package cx.ath.mancel01.dependencyshot.test.instance;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import javax.inject.Provider;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceFluentBinder extends Binder {

    public static final String MY_MODULE = "MyModule";
    public static final String MY_OTHER_MODULE = "MyOtherModule";
    public static final String YAM = "YetAnotherModule";

    @Override
    public void configureBindings() {
        fbind(String.class).annotedWith(MyModule.class).providedBy(new Provider(){
            @Override
            public Object get() {
                return MY_MODULE;
            }
        }); // TODO : heavy stuff to configure -> fbind(T).toInstance(? extends T)
        fbind(String.class).annotedWith(MyOtherModule.class).providedBy(new Provider(){
            @Override
            public Object get() {
                return MY_OTHER_MODULE;
            }
        });
        fbind(String.class).named("yam").providedBy(new Provider(){
            @Override
            public Object get() {
                return YAM;
            }
        });
        fbind(InstanceModule.class);
        fbind(InstanceModule2.class);
        fbind(InstanceModule3.class);
    }
}
