package cx.ath.mancel01.dependencyshot.test.instance;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class InstanceTest {
    /**
     * Injection test.
     */
    @Test
    public void testSpecificInstancesInjection() {
        DSInjector injector = DependencyShot.getInjector(new InstanceBinder());
        Module module1 = injector.getInstance(InstanceModule.class);
        Module module2 = injector.getInstance(InstanceModule2.class);
        Module module3 = injector.getInstance(InstanceModule3.class);
        module1.start();
        module2.start();
        module3.start();
        assertTrue(module1.getName()
                .equals(InstanceBinder.MY_MODULE));
        assertTrue(module2.getName()
                .equals(InstanceBinder.MY_OTHER_MODULE));
        assertTrue(module3.getName()
                .equals(InstanceBinder.YAM));
    }

    @Test
    public void testEasySpecificInstancesInjection() {
        DSInjector injector = DependencyShot.getInjector(new EasyInstanceBinder());
        Module module1 = injector.getInstance(InstanceModule.class);
        Module module2 = injector.getInstance(InstanceModule2.class);
        Module module3 = injector.getInstance(InstanceModule3.class);
        module1.start();
        module2.start();
        module3.start();
        assertTrue(module1.getName()
                .equals(EasyInstanceBinder.MY_MODULE));
        assertTrue(module2.getName()
                .equals(EasyInstanceBinder.MY_OTHER_MODULE));
        assertTrue(module3.getName()
                .equals(EasyInstanceBinder.YAM));
    }
}
