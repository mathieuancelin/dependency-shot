package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.annotations.Interceptors;

@Interceptors(value = {MyInterceptor.class, MySecondInterceptor.class})
public class ServiceImpl implements Service {

    private boolean gone = false;

    @Override
    public void go() {
        System.out.println("Service impl");
        gone = false;
    }

    @Override
    public boolean isGone() {
        return gone;
    }

    @Interceptors(value = MyMethodInterceptor.class)
    @Override
    public String getString() {
        return "the returned String";
    }

    @Override
    public int getInt() {
        return 1;
    }

    @Override
    public void setString(String str) {
        System.out.println("set string " + str);
    }
}
