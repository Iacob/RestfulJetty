package luoyong.restfuljetty.jersey

import javax.ws.rs.ApplicationPath

import luoyong.restfuljetty.rs.{Res1, User}
import org.glassfish.jersey.server.ResourceConfig

/**
  *
  */
@ApplicationPath("/")
class JerseyApplication extends ResourceConfig {
  //register(new Res1(), null)
  registerClasses(classOf[Res1])
  registerClasses(classOf[Res1], classOf[User])
}
