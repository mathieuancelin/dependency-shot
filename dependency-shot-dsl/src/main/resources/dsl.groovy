import cx.ath.mancel01.dependencyshot.dsl.BasicService
import cx.ath.mancel01.dependencyshot.dsl.BasicServiceImpl

bindings {

    binding {
        from = BasicService.class
        to = BasicServiceImpl.class
    }

}