package cx.ath.mancel01.dependencyshot.test.basic;

import javax.inject.Inject;

/**
 * Test client.
 * 
 * @author Mathieu ANCELIN
 */
public class BasicClient {

    /**
     * A service.
     */
    private final BasicService service;
    /**
     * Another service.
     */
    @Inject
    private BasicService service2;
    /**
     * Yet another service.
     */
    private BasicService service3;

    /**
     * Injected constructor.
     * @param service injected object.
     */
    @Inject
    public BasicClient(BasicService service) {
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
    public void setService2(BasicService service2) {
        this.service2 = service2;
    }

    /**
     * Injected setter.
     * @param service3 a service.
     */
    @Inject
    public void setService3(BasicService service3) {
        this.service3 = service3;
    }

    /**
     * Getter.
     * @return a service.
     */
    public BasicService getService() {
        return this.service;
    }

    /**
     * Getter.
     * @return a service.
     */
    public BasicService getService2() {
        return service2;
    }

    /**
     * Getter.
     * @return a service.
     */
    public BasicService getService3() {
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
