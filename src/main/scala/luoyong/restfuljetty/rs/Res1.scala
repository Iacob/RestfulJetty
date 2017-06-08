package luoyong.restfuljetty.rs

import javax.ws.rs.{GET, Path, Produces}

/**
  *
  */
@Path("/rs1")
class Res1 {

  @Path("/m1")
  @Produces(Array("text/plain"))
  @GET
  def m1():String = {
    return "Hola."
  }
}
