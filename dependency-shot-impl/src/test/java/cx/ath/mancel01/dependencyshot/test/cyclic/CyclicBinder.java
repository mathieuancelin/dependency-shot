package cx.ath.mancel01.dependencyshot.test.cyclic;

import cx.ath.mancel01.dependencyshot.graph.Binder;
/**
 * Test Binder.
 * 
 * @author Mathieu ANCELIN
 */
public class CyclicBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(BillingService.class);
        bind(LoggerService.class);
        bind(Account.class);
        bind(WhichLoggerToChooseService.class);
    }
}
