package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.api.annotations.ManagedBean;
import cx.ath.mancel01.dependencyshot.api.annotations.PostConstruct;
import cx.ath.mancel01.dependencyshot.api.annotations.PreDestroy;

/**
 * Implementation of a service.
 * 
 * @author Mathieu ANCELIN
 */
@ManagedBean
public class LifecycleServiceImpl implements LifecycleService {
    /**
     * A boolean.
     */
    private boolean gone = false;

    @Override
    public void go() {
        gone = false;
    }

    @Override
    public boolean isGone() {
        return gone;
    }

    @Override
    public String getString() {
        return "the returned String";
    }

    @Override
    public int getInt() {
        return 1;
    }

    @Override
    public void setString(final String str) {
        System.out.println("set string " + str);
    }

    @PostConstruct
    private void load() {
        //System.out.println("Load LifecycleServiceImpl : " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementLoad();
    }
    @PreDestroy
    private void unload() {
        //System.out.println("Unload LifecycleServiceImpl : " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementUnload();
    }
}
