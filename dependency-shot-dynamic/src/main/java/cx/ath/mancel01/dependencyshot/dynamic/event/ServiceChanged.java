package cx.ath.mancel01.dependencyshot.dynamic.event;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

/**
 *
 * @author mathieu
 */
public class ServiceChanged extends AbstractServiceEvent {

     public ServiceChanged(
            ServiceReference ref, BundleContext context) {
        super(ref, context);
    }

    @Override
    public EventType eventType() {
        return EventType.SERVICE_CHANGED;
    }
}