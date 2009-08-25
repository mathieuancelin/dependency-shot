package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.api.annotations.Interceptors;

@Interceptors(value = MyInterfaceInterceptor.class)
public interface Service {

    void go();

    String getString();

    void setString(String str);
    
    @Interceptors(value = MyMethodInterceptor.class)
    int getInt();

    boolean isGone();
}
