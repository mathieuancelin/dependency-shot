package cx.ath.mancel01.dependencyshot.test.aop;

import cx.ath.mancel01.dependencyshot.api.DSInvocationContext;
import javax.interceptor.AroundInvoke;

/**
 * An interceptor class.
 *
 * @author Mathieu ANCELIN
 */
public class AOPInterceptor {
    /**
     * Interception method.
     * @param ctx the invocation context.
     * @return the result of the invocation.
     * @throws Exception invocation exception
     */
    @AroundInvoke
    public Object intercept(final DSInvocationContext ctx) throws Exception {
        AOPInterceptionResult.getInstance().getPreClass().add("before interface invocation " + ctx.getBean().getClass().getName()
                + ":" + ctx.getMethod().getName());
        try {
            return ctx.proceed();
        } finally {
            AOPInterceptionResult.getInstance().getPostClass().add("after  interface invocation " + ctx.getBean().getClass().getName()
                    + ":" + ctx.getMethod().getName());
        }
    }
}
