package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.api.annotations.PostConstruct;
import cx.ath.mancel01.dependencyshot.api.annotations.PreDestroy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Inject;

/**
 * Test client.
 * 
 * @author Mathieu ANCELIN
 */
public class LifecycleClient {

    /**
     * A service.
     */
    private final LifecycleService service;
    /**
     * Another service.
     */
    @Inject
    private LifecycleService service2;
    /**
     * Yet another service.
     */
    private LifecycleService service3;

    /**
     * Injected constructor.
     * @param service injected object.
     */
    @Inject
    public LifecycleClient(LifecycleService service) {
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
    public void setService2(LifecycleService service2) {
        this.service2 = service2;
    }

    /**
     * Injected setter.
     * @param service3 a service.
     */
    @Inject
    public void setService3(LifecycleService service3) {
        this.service3 = service3;
    }

    /**
     * Getter.
     * @return a service.
     */
    public LifecycleService getService() {
        return this.service;
    }

    /**
     * Getter.
     * @return a service.
     */
    public LifecycleService getService2() {
        return service2;
    }

    /**
     * Getter.
     * @return a service.
     */
    public LifecycleService getService3() {
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

    @PostConstruct
    public void load() {
//        Logger.getLogger(LifecycleClient.class.getName()).
//                            log(Level.INFO, "Load " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementLoad();
    }
    @PreDestroy
    public void unload() {
//        Logger.getLogger(LifecycleClient.class.getName()).
//                            log(Level.INFO, "Unload " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementUnload();
    }
}
