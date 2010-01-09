package cx.ath.mancel01.dependencyshot.test.lifecycle;

import cx.ath.mancel01.dependencyshot.api.annotations.PostConstruct;
import cx.ath.mancel01.dependencyshot.api.annotations.PreDestroy;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Implementation of a service.
 * 
 * @author Mathieu ANCELIN
 */
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
//        Logger.getLogger(LifecycleServiceImpl.class.getName()).
//                            log(Level.INFO, "Load " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementLoad();
    }
    @PreDestroy
    private void unload() {
//        Logger.getLogger(LifecycleServiceImpl.class.getName()).
//                            log(Level.INFO, "Unload " + this.getClass().getName());
        LifecycleCounter.getInstance().incrementUnload();
    }
}
