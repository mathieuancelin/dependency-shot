package cx.ath.mancel01.dependencyshot.test.aop;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import org.junit.Test;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class AOPInjectTest {
    /**
     * Injection test.
     */
    @Test
    public void testInjectedClient() {
        //System.out.println("Test AOP IMPL");
        DSInjector injector = DependencyShot.getInjector(new AOPBinder());
        AOPClient client = injector.getInstance(AOPClient.class);
        client.go();
        client.getInt();
        assertTrue(AOPInterceptionResult.getInstance().getPreMethod().size() == 1);
        assertTrue(AOPInterceptionResult.getInstance().getPostMethod().size() == 1);
        assertTrue(AOPInterceptionResult.getInstance().getPreClass().size() == 12);
        assertTrue(AOPInterceptionResult.getInstance().getPostClass().size() == 12);
        AOPInterceptionResult.getInstance().reset();
    }
    @Test
    public void testFluentInjectedClient() {
        //System.out.println("Test AOP IMPL Fluent");
        DSInjector injector = DependencyShot.getInjector(new AOPFluentBinder());
        AOPClient client = injector.getInstance(AOPClient.class);
        client.go();
        client.getInt();
        assertTrue(AOPInterceptionResult.getInstance().getPreMethod().size() == 1);
        assertTrue(AOPInterceptionResult.getInstance().getPostMethod().size() == 1);
        assertTrue(AOPInterceptionResult.getInstance().getPreClass().size() == 12);
        assertTrue(AOPInterceptionResult.getInstance().getPostClass().size() == 12);
        AOPInterceptionResult.getInstance().reset();
    }
}
