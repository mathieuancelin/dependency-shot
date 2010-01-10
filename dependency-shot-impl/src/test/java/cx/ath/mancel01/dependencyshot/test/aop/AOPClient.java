package cx.ath.mancel01.dependencyshot.test.aop;

import cx.ath.mancel01.dependencyshot.api.annotations.ManagedBean;
import javax.inject.Inject;

/**
 * Test client.
 * 
 * @author Mathieu ANCELIN
 */
@ManagedBean
public class AOPClient {

    /**
     * A service.
     */
    private final AOPService service;
    /**
     * Another service.
     */
    @Inject
    private AOPService service2;
    /**
     * Yet another service.
     */
    private AOPService service3;

    /**
     * Injected constructor.
     * @param service injected object.
     */
    @Inject
    public AOPClient(AOPService service) {
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
    public void setService2(AOPService service2) {
        this.service2 = service2;
    }

    /**
     * Injected setter.
     * @param service3 a service.
     */
    @Inject
    public void setService3(AOPService service3) {
        this.service3 = service3;
    }

    /**
     * Getter.
     * @return a service.
     */
    public AOPService getService() {
        return this.service;
    }

    /**
     * Getter.
     * @return a service.
     */
    public AOPService getService2() {
        return service2;
    }

    /**
     * Getter.
     * @return a service.
     */
    public AOPService getService3() {
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
