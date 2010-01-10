package cx.ath.mancel01.dependencyshot.test.aop;

import cx.ath.mancel01.dependencyshot.api.DSInvocationContext;
import cx.ath.mancel01.dependencyshot.api.annotations.AroundInvoke;
import javax.inject.Inject;

/**
 * An interceptor class.
 *
 * @author Mathieu ANCELIN
 */
public class AOPMethodInterceptor {
    @Inject
    private AOPInjectedInInterceptor printer;
    /**
     * Interception method.
     * @param ctx the invocation context.
     * @return the result of the invocation.
     * @throws Exception invocation exception
     */
    @AroundInvoke
    public Object intercept(final DSInvocationContext ctx) throws Exception {
        AOPInterceptionResult.getInstance().getPreMethod().add("before method invocation " + ctx.getBean().getClass().getName() + ":"
                + ctx.getMethod().getName());
        printer.printIntercepted();
        try {
            return ctx.proceed();
        } finally {
            AOPInterceptionResult.getInstance().getPostMethod().add("after  method invocation " + ctx.getBean().getClass().getName()
                    + ":" + ctx.getMethod().getName());
        }
    }
}
