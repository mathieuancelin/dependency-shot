package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.Injector;
import cx.ath.mancel01.dependencyshot.graph.Binder;
import javax.inject.Inject;
import static junit.framework.Assert.assertTrue;

public class InjectTest {

    public interface Service {

        void go();

        boolean isGone();
    }

    public static class ServiceImpl implements Service {

        private boolean gone = false;

        @Override
        public void go() {
            System.out.println("Service impl");
            gone = false;
        }

        @Override
        public boolean isGone() {
            return gone;
        }
    }

    public static class MyBinder extends Binder {

        @Override
        public void configureBindings() {
            bind(Service.class, ServiceImpl.class);
        }
    }

    public static class Client {

        private final Service service;
        @Inject
        private Service service2;
        private Service service3;

        @Inject //test constructor injection
        public Client(Service service) {
            this.service = service;
        }

        public void go() {
            service.go();
            service2.go();
            service3.go();
        }

        public void setService2(Service service2) {
            this.service2 = service2;
        }

        @Inject
        public void setService3(Service service3) {
            this.service3 = service3;
        }

        public Service getService() {
            return this.service;
        }

        public Service getService2() {
            return service2;
        }

        public Service getService3() {
            return service3;
        }
    }

    public void testMockedClient() {
        System.out.println("Test : Mock");
        MockService mock = new MockService();
        Client client = new Client(mock);
        client.setService2(mock);
        client.setService3(mock);
        client.go();
        assertTrue(client.getService().isGone());
        assertTrue(client.getService2().isGone());
        assertTrue(client.getService3().isGone());
    }

    public void testInjectedClient() {
        System.out.println("Test : @Inject");
        Injector injector = Shot.getInjector(new MyBinder());
        Client client = injector.getObjectInstance(Client.class);
        client.go();
        assertTrue(!client.getService().isGone());
        assertTrue(!client.getService2().isGone());
        assertTrue(!client.getService3().isGone());
    }

    public static class MockService implements Service {

        private boolean gone = false;

        @Override
        public void go() {
            System.out.println("Service mock");
            gone = true;
        }

        @Override
        public boolean isGone() {
            return gone;
        }
    }
}
