package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class LifecycleInjectTest {
    /**
     * Injection test.
     */
    public void testInjectedClient() {
        DSInjector injector = DependencyShot.getInjector(new LifecycleBinder());
        LifecycleClient client = injector.getInstance(LifecycleClient.class);
        client.go();
        assertTrue(LifecycleCounter.getInstance().getLoadCounter() == 4);
    }
}
