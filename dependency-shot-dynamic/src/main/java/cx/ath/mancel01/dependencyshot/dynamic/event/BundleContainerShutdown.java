package cx.ath.mancel01.dependencyshot.dynamic.event;

import org.osgi.framework.BundleContext;

/**
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public class BundleContainerShutdown {

    private BundleContext bundleContext;

    public BundleContainerShutdown(final BundleContext context) {
        this.bundleContext = context;
    }

    public BundleContext getBundleContext() {
        return bundleContext;
    }
}
