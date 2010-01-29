package cx.ath.mancel01.dependencyshot.test.cyclic;

import cx.ath.mancel01.dependencyshot.graph.Binder;
import javax.inject.Provider;

/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class CyclicBinder extends Binder {

    @Override
    public void configureBindings() {
        fbind(BillingService.class);
        fbind(LoggerService.class);
        fbind(Account.class);
        //fbind(WhichLoggerToChooseService.class);
        fbind(WhichLoggerToChooseService.class).providedBy(new Provider() {
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
