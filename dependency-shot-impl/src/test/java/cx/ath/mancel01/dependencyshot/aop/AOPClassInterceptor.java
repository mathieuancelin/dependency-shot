package cx.ath.mancel01.dependencyshot.aop;

import cx.ath.mancel01.dependencyshot.api.DSInvocationContext;
import cx.ath.mancel01.dependencyshot.api.annotations.AroundInvoke;

/**
 * An interceptor class.
 *
 * @author Mathieu ANCELIN
 */
public class AOPClassInterceptor {
    /**
     * Interception method.
     * @param ctx the invocation context.
     * @return the result of the invocation.
     * @throws Exception invocation exception
     */
    @AroundInvoke
    public Object intercept(final DSInvocationContext ctx) throws Exception {
        AOPInterceptionResult.getInstance().getPreClass().add("before invocation " + ctx.getBean().getClass().getName()
                + ":" + ctx.getMethod().getName());
        try {
            return ctx.proceed();
        } finally {
            
            AOPInterceptionResult.getInstance().getPostClass().add("after  invocation " + ctx.getBean().getClass().getName()
                    + ":" + ctx.getMethod().getName());
        }
    }
}
