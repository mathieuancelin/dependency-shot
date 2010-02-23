package cx.ath.mancel01.dependencyshot.test.cyclic;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class CyclicTest {

    @Test
    public void testCyclicDependency() {
        boolean isCyclic = false;
        Exception ex = null;
        try {
            DSInjector injector = DependencyShot.getInjector();
            BillingService service = injector.getInstance(BillingService.class);
            service.chargeAccountFor(123);
            assertTrue(service.getAccount().getMoney() == (100000 - 123));
        } catch (Exception e) {
            ex = e;
            isCyclic = true;
        }
        assertTrue(isCyclic);
        assertTrue(ex.getMessage().contains("DSCyclicDependencyDetectedException"));
    }

    @Test
    public void testNonCyclicDependency() {
        DSInjector injector = DependencyShot.getInjector(new NonCyclicBinder());
        BillingService service = injector.getInstance(BillingService.class);
        service.chargeAccountFor(123);
        assertTrue(service.getAccount().getMoney() == (100000 - 123));
    }
}
