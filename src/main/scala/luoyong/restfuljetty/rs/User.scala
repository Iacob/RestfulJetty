package luoyong.restfuljetty.rs

import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs._
import collection.JavaConverters._

import org.apache.commons.io.IOUtils

/**
  *
  */
@Path("/user")
class User {

  @Path("/list")
  @Consumes(Array("application/x-www-form-urlencoded"))
  @Produces(Array("application/json"))
  @POST
  def list(@Context request:HttpServletRequest):String = {

    //println(columns)
    request.getParameterNames.asScala.foreach((name) => {println(name)})
    request.getParameterMap.asScala.foreach((kv) => {println(kv._1)})

    val req = IOUtils.toString(request.getInputStream, StandardCharsets.UTF_8)
    println(req)

    return """

    {
      "draw": 1,
      "recordsTotal": 2,
      "recordsFiltered": 2,
      "data": [
      [
      "Angelica",
      "Ramos",
      "System Architect",
      "London",
      "9th Oct 09",
      "$2,875"
      ],
      [
      "Ashton",
      "Cox",
      "Technical Author",
      "San Francisco",
      "12th Jan 09",
      "$4,800"
      ]
      ]
    }
      """
  }
}
