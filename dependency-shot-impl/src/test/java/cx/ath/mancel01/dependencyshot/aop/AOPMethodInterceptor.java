package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.api.DSInvocationContext;
import cx.ath.mancel01.dependencyshot.api.annotations.AroundInvoke;

/**
 * An interceptor class.
 *
 * @author Mathieu ANCELIN
 */
public class AOPMethodInterceptor {
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
        try {
            return ctx.proceed();
        } finally {
            AOPInterceptionResult.getInstance().getPostMethod().add("after  method invocation " + ctx.getBean().getClass().getName()
                    + ":" + ctx.getMethod().getName());
        }
    }
}
