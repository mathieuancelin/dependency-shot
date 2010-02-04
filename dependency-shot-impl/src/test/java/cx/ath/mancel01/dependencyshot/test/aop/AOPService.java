package cx.ath.mancel01.dependencyshot.test.aop;

import javax.interceptor.Interceptors;

/**
 * Interceptor on any implementation of this interface.
 *
 * @author Mathieu ANCELIN
 */
@Interceptors(value = AOPInterceptor.class)
public interface AOPService {
    /**
     * Some method.
     */
    void go();
    /**
     * @return a string.
     */
    String getString();
    /**
     * Setter.
     * @param str a string.
     */
    void setString(String str);
    /**
     * @return an int.
     */
    @Interceptors(value = AOPMethodInterceptor.class)
    int getInt();
    /**
     * @return gone boolean.
     */
    boolean isGone();
}
