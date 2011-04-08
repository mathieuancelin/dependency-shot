package cx.ath.mancel01.dependencyshot.dynamic.integration;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public class DynamicServiceHandler implements InvocationHandler {

    private final String name;
    private final BundleContext registry;

    public DynamicServiceHandler(String name, BundleContext registry) {
        this.name = name;
        this.registry = registry;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ServiceReference reference = registry.getServiceReference(name);
        Object instanceToUse = registry.getService(reference);
        try {
            return method.invoke(instanceToUse, args);
        } finally {
            registry.ungetService(reference);
        }
    }
}
