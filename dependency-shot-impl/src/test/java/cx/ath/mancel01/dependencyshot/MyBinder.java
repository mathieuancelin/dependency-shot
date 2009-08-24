package cx.ath.mancel01.dependencyshot;

import cx.ath.mancel01.dependencyshot.graph.Binder;

public class MyBinder extends Binder {

    @Override
    public void configureBindings() {
        bind(Service.class, ServiceImpl.class);
    }
}
