package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class LifecycleInjectTest {
    @Test
    public void testFluentInjectedClient() {
        DSInjector injector = DependencyShot.getInjector(new LifecycleFluentBinder());
        LifecycleClient client = injector.getInstance(LifecycleClient.class);
        client.go();
        assertTrue(LifecycleCounter.getInstance().getLoadCounter() == 4);
    }
}
