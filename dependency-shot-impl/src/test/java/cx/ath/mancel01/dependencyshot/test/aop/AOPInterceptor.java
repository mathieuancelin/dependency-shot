package cx.ath.mancel01.dependencyshot.test.aop;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

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
    public Object intercept(final InvocationContext ctx) throws Exception {
        AOPInterceptionResult.getInstance().getPreClass().add("before interface invocation " 
                + ctx.getTarget().getClass().getName()
                + ":" + ctx.getMethod().getName());
        try {
            return ctx.proceed();
        } finally {
            AOPInterceptionResult.getInstance().getPostClass().add("after  interface invocation " 
                    + ctx.getTarget().getClass().getName()
                    + ":" + ctx.getMethod().getName());
        }
    }
}
