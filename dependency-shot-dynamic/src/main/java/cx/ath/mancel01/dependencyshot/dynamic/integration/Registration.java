package cx.ath.mancel01.dependencyshot.dynamic.integration;

/**
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public interface Registration<T> {

    void unregister();

    <T> Service<T> getServiceReference();

}
