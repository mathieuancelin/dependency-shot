package cx.ath.mancel01.dependencyshot.dynamic.integration;

/**
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public interface Registrations<T> extends Iterable<Registration<T>> {

    int size();
}
