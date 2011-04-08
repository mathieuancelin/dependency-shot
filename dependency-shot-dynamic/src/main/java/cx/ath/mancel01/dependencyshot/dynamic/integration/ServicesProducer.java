package cx.ath.mancel01.dependencyshot.dynamic.integration;

import cx.ath.mancel01.dependencyshot.api.InjectionPoint;
import cx.ath.mancel01.dependencyshot.injection.util.EnhancedProvider;
import java.lang.reflect.ParameterizedType;
import javax.inject.Inject;
import javax.inject.Provider;
import org.osgi.framework.BundleContext;

/**
 * Producers for Specific injected types;
 *
 * @author Mathieu ANCELIN - SERLI (mathieu.ancelin@serli.com)
 */
public class ServicesProducer {

    public static class ContextProvider implements Provider<BundleContext> {

        @Inject BundleContextHolder holder;

        @Override
        public BundleContext get() {
            return holder.getContext();
        }
    }

    public static class OSGiServicesProvider implements EnhancedProvider<ServicesImpl> {

        @Inject BundleContext context;

        @Override
        public ServicesImpl enhancedGet(InjectionPoint p) {
            return new ServicesImpl(((ParameterizedType)p.getType()).getActualTypeArguments()[0],
                p.getMember().getDeclaringClass(), context);
        }

        @Override
        public Object get() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static class OSGiServiceProvider implements EnhancedProvider<ServiceImpl> {

        @Inject BundleContext context;

        @Override
        public ServiceImpl enhancedGet(InjectionPoint p) {
            return new ServiceImpl(((ParameterizedType)p.getType()).getActualTypeArguments()[0],
                p.getMember().getDeclaringClass(), context);
        }

        @Override
        public Object get() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static class RegistrationsProvider implements EnhancedProvider<Registrations> {

        @Inject BundleContext context;
        @Inject RegistrationsHolder holder;

        @Override
        public Registrations enhancedGet(InjectionPoint p) {
            RegistrationsImpl registration = new RegistrationsImpl();
            registration.setType(((Class) ((ParameterizedType)p.getType()).getActualTypeArguments()[0]));
            registration.setHolder(holder);
            registration.setRegistry(context);
            return registration;
        }

        @Override
        public Object get() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
