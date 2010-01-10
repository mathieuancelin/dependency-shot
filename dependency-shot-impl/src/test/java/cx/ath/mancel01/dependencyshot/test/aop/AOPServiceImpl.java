package cx.ath.mancel01.dependencyshot.test.aop;

import cx.ath.mancel01.dependencyshot.api.annotations.Interceptors;
import cx.ath.mancel01.dependencyshot.api.annotations.ManagedBean;

/**
 * Implementation of a service.
 * 
 * @author Mathieu ANCELIN
 */
@ManagedBean
@Interceptors(value = {AOPClassInterceptor.class, AOPSecondClassInterceptor.class})
public class AOPServiceImpl implements AOPService {
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

    @Interceptors(value = AOPMethodInterceptor.class)
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
}
