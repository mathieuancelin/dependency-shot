package cx.ath.mancel01.dependencyshot.dynamic.event;

import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public class BundleContainerInitialized {

    private BundleContext bundleContext;

    public BundleContainerInitialized(final BundleContext context) {
        this.bundleContext = context;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }
}
