package cx.ath.mancel01.dependencyshot.dynamic.integration;

/**
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public interface Services<T> extends Iterable<T> {

    int size();
}
