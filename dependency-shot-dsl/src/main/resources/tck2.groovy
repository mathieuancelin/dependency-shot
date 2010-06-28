import org.atinject.tck.auto.Car
import org.atinject.tck.auto.Convertible
import org.atinject.tck.auto.Seat
import org.atinject.tck.auto.DriversSeat
import org.atinject.tck.auto.Drivers
import org.atinject.tck.auto.Engine
import org.atinject.tck.auto.V8Engine
import org.atinject.tck.auto.Tire
import cx.ath.mancel01.dependencyshot.dsl.tck.TireProvider

bindings {
    
    bind Car.class, to: Convertible.class

    bind Seat.class, annotedWith: Drivers.class, to: DriversSeat.class
    
    bind Engine.class, to: V8Engine.class
    
    bind Tire.class, named: "spare", providedBy: new TireProvider()
    
}