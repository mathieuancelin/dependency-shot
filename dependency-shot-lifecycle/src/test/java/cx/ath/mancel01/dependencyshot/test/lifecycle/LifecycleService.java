package cx.ath.mancel01.dependencyshot.test.lifecycle;

/**
 * Interceptor on any implementation of this interface.
 *
 * @author Mathieu ANCELIN
 */
public interface LifecycleService {
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
    int getInt();
    /**
     * @return gone boolean.
     */
    boolean isGone();
}