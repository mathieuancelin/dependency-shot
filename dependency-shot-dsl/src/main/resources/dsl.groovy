import cx.ath.mancel01.dependencyshot.dsl.BasicService
import cx.ath.mancel01.dependencyshot.dsl.BasicServiceImpl

bindings {
    binding {
        from = cx.ath.mancel01.dependencyshot.dsl.BasicService.class
        to = cx.ath.mancel01.dependencyshot.dsl.BasicServiceImpl.class
    }
}