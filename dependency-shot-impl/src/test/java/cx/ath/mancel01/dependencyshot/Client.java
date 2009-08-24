package cx.ath.mancel01.dependencyshot;

import javax.inject.Inject;

public class Client {

    private final Service service;

    @Inject
    private Service service2;
    
    private Service service3;

    @Inject
    public Client(Service service) {
        super();
        this.service = service;
    }

    public void go() {
        service.go();
        service2.go();
        service3.go();
    }

    public void setService2(Service service2) {
        this.service2 = service2;
    }

    @Inject
    public void setService3(Service service3) {
        this.service3 = service3;
    }

    public Service getService() {
        return this.service;
    }

    public Service getService2() {
        return service2;
    }

    public Service getService3() {
        return service3;
    }

    public String getString(){
        return service.getString();
    }

    public int getInt(){
        return service.getInt();
    }

    public void setString(String str){
        service.setString(str);
    }
}
