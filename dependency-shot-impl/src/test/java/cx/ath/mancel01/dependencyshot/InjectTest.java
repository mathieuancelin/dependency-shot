package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.DSInjector;
import static junit.framework.Assert.assertTrue;

public class InjectTest {

    public void testMockedClient() {
        System.out.println("Test : Mock");
        MockService mock = new MockService();
        Client client = new Client(mock);
        client.setService2(mock);
        client.setService3(mock);
        client.go();
        client.setString("blabla");
        System.out.println(client.getString());
        System.out.println(client.getInt());
        assertTrue(client.getService().isGone());
        assertTrue(client.getService2().isGone());
        assertTrue(client.getService3().isGone());
    }

    public void testInjectedClient() {
        System.out.println("Test : @Inject");
        DSInjector injector = Shot.getInjector(new MyBinder());
        Client client = injector.getObjectInstance(Client.class);
        client.go();
        client.setString("blabla");
        System.out.println(client.getString());
        System.out.println(client.getInt());
        assertTrue(!client.getService().isGone());
        assertTrue(!client.getService3().isGone());
        assertTrue(!client.getService2().isGone());
        
    }
}
