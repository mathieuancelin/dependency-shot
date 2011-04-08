package cx.ath.mancel01.dependencyshot.dynamic.integration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public class ServiceReferenceHandler implements InvocationHandler {

    private final ServiceReference ref;
    private final BundleContext registry;

    public ServiceReferenceHandler(ServiceReference ref, BundleContext registry) {
        this.ref = ref;
        this.registry = registry;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object instanceToUse = registry.getService(ref);
        try {
            return method.invoke(instanceToUse, args);
        } finally {
            registry.ungetService(ref);
        }
    }
}
