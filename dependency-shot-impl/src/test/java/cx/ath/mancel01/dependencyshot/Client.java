package cx.ath.mancel01.dependencyshot;

import javax.inject.Inject;

/**
 * Test client.
 * 
 * @author Mathieu ANCELIN
 */
public class Client {

    /**
     * A service.
     */
    private final Service service;
    /**
     * Another service.
     */
    @Inject
    private Service service2;
    /**
     * Yet another service.
     */
    private Service service3;

    /**
     * Injected constructor.
     * @param service injected object.
     */
    @Inject
    public Client(Service service) {
        super();
        this.service = service;
    }

    /**
     * Business method.
     */
    public void go() {
        service.go();
        service2.go();
        service3.go();
    }

    /**
     * Setter.
     * @param service2 a service.
     */
    public void setService2(Service service2) {
        this.service2 = service2;
    }

    /**
     * Injected setter.
     * @param service3 a service.
     */
    @Inject
    public void setService3(Service service3) {
        this.service3 = service3;
    }

    /**
     * Getter.
     * @return a service.
     */
    public Service getService() {
        return this.service;
    }

    /**
     * Getter.
     * @return a service.
     */
    public Service getService2() {
        return service2;
    }

    /**
     * Getter.
     * @return a service.
     */
    public Service getService3() {
        return service3;
    }

    /**
     * Getter.
     * @return a string.
     */
    public String getString() {
        return service.getString();
    }

    /**
     * Getter.
     * @return an int.
     */
    public int getInt() {
        return service.getInt();
    }

    /**
     * Setter.
     * @param str a string.
     */
    public void setString(String str) {
        service.setString(str);
    }
}
