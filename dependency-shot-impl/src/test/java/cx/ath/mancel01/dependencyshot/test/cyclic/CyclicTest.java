package cx.ath.mancel01.dependencyshot.test.cyclic;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class CyclicTest {

    public void testCyclicDependency() {
        DSInjector injector = DependencyShot.getInjector(new CyclicBinder());
        BillingService service = injector.getInstance(BillingService.class);
        service.chargeAccountFor(123);
        assertTrue(service.getAccount().getMoney() == (100000 - 123));
    }
}
