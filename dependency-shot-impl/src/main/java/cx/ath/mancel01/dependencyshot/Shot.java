package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.DSBinder;
import cx.ath.mancel01.dependencyshot.injection.DefaultInjector;
import cx.ath.mancel01.dependencyshot.injection.InjectorImpl;
import cx.ath.mancel01.dependencyshot.injection.InjectorMaker;
import java.util.Arrays;

public class Shot
{

    public static InjectorImpl getInjector(String xmlBindingFile) {
        return InjectorMaker.makeInjector(xmlBindingFile); // TODO xml configuration
    }

    public static InjectorImpl getInjector(DSBinder... injModules) {
        return getInjector(Arrays.asList(injModules));
    }

    public static InjectorImpl getInjector(Iterable<? extends DSBinder> injModules) {
        return InjectorMaker.makeInjector(injModules);
    }

    public static DefaultInjector getDefaultInjector(){
        return DefaultInjector.getInstance();
    }
}
