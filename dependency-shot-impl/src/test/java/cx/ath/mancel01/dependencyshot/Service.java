package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.annotations.Interceptors;

/**
 * Interceptor on any implementation of this interface.
 *
 * @author Mathieu ANCELIN
 */
@Interceptors(value = MyInterfaceInterceptor.class)
public interface Service {
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
    @Interceptors(value = MyMethodInterceptor.class)
    int getInt();
    /**
     * @return gone boolean.
     */
    boolean isGone();
}
