import cx.ath.mancel01.dependencyshot.dsl.BasicService
import cx.ath.mancel01.dependencyshot.dsl.BasicServiceImpl

bindings {

    bind BasicService.class, to: BasicServiceImpl.class
    
}
