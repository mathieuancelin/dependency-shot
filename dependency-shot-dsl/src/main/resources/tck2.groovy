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
    
    bind from: Car.class, to: Convertible.class

    bind from: Seat.class, to: DriversSeat.class, annotedWith: Drivers.class
    
    bind from: Engine.class, to: V8Engine.class
    
    bind from: Tire.class, named: "spare", providedBy: new TireProvider()
    
}