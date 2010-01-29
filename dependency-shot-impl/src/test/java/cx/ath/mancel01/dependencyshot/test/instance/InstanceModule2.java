package cx.ath.mancel01.dependencyshot.test.instance;

import javax.inject.Inject;

/**
 * Implementation of a service.
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceModule2  implements Module {

    @Inject @MyOtherModule
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
