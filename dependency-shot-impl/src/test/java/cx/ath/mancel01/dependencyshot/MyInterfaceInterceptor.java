package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.DSInvocationContext;
import cx.ath.mancel01.dependencyshot.api.annotations.AroundInvoke;

public class MyInterfaceInterceptor {

    @AroundInvoke
    public Object intercept(DSInvocationContext ctx) throws Exception {
        System.out.println("before interface invocation " + ctx.getBean().getClass().getName() + ":" + ctx.getMethod().getName());
        try {
            return ctx.proceed();
        } finally {
            System.out.println("after  interface invocation " + ctx.getBean().getClass().getName() + ":" + ctx.getMethod().getName());
        }
    }
}
