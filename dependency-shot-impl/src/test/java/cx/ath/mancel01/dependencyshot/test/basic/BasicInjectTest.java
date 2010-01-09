package cx.ath.mancel01.dependencyshot.test.basic;

import cx.ath.mancel01.dependencyshot.DependencyShot;
import cx.ath.mancel01.dependencyshot.api.DSInjector;
import static junit.framework.Assert.assertTrue;

/**
 * Tests of dependency-shot. 
 * 
 * @author Mathieu ANCELIN
 */
public class BasicInjectTest {
    /**
     * Mock test.
     */
    public void testMockedClient() {
        System.out.println("Test : Mock");
        BasicMockService mock = new BasicMockService();
        BasicClient client = new BasicClient(mock);
        client.setService2(mock);
        client.setService3(mock);
        client.go();
        assertTrue(client.getService().isGone());
        assertTrue(client.getService2().isGone());
        assertTrue(client.getService3().isGone());
    }
    /**
     * Injection test.
     */
    public void testInjectedClient() {
        System.out.println("Test : @Inject");
        DSInjector injector = DependencyShot.getInjector(new BasicBinder());
        BasicClient client = injector.getInstance(BasicClient.class);
        client.go();
        assertTrue(!client.getService().isGone());
        assertTrue(!client.getService3().isGone());
        assertTrue(!client.getService2().isGone());
        
    }
}
