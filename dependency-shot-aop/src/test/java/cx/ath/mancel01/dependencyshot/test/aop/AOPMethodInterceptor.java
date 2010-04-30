package cx.ath.mancel01.dependencyshot.test.aop;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

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
    public Object intercept(final InvocationContext ctx) throws Exception {
        AOPInterceptionResult.getInstance().getPreMethod().add("before method invocation " 
                + ctx.getTarget().getClass().getName() + ":"
                + ctx.getMethod().getName());
        printer.printIntercepted();
        try {
            return ctx.proceed();
        } finally {
            AOPInterceptionResult.getInstance().getPostMethod().add("after  method invocation " 
                    + ctx.getTarget().getClass().getName()
                    + ":" + ctx.getMethod().getName());
        }
    }
}
