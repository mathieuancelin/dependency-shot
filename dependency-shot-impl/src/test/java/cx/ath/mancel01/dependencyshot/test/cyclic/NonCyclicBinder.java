package cx.ath.mancel01.dependencyshot.test.cyclic;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import javax.inject.Provider;

/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class NonCyclicBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(WhichLoggerToChooseService.class).providedBy(new Provider() {
            @Override
            public Object get() {
                WhichLoggerToChooseService service = new WhichLoggerToChooseService();
                LoggerService logger = new LoggerService();
                logger.setWhich(service);
                service.setLogger(logger);
                return service;
            }
        });
    }
}
