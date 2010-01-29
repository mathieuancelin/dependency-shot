package cx.ath.mancel01.dependencyshot.test.instance;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Implementation of a service.
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceModule3  implements Module {

    @Inject @Named("yam")
    private String name;

    @Override
    public void start() {
        System.out.println("Starting module : " + name);
    }

    @Override
    public String getName() {
        return this.name;
    }
}
