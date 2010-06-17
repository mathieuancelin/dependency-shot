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
    binding {
        from = Car.class
        to = Convertible.class
    }
    binding {
        from = Seat.class
        to = DriversSeat.class
        qualifier = Drivers.class
    }
    binding {
        from = Engine.class
        to = V8Engine.class
    }
    binding {
        from = Tire.class
        name = "spare"
        provider = new TireProvider()
    }
}