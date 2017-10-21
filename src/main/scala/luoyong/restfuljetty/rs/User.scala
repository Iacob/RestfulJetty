package luoyong.restfuljetty.rs

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs._

import collection.JavaConverters._
import org.apache.commons.io.IOUtils

import scala.collection.mutable

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

    val formKv = extractForm(req)
    println(formKv.toString())

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

  def extractForm(formStr:String):Map[String, String] = {
    val formKv = formStr.split("&")
    val result:mutable.HashMap[String, String] = new mutable.HashMap[String, String]()
    formKv.foreach((kvStr) => {
      val kvArr = kvStr.split("=")
      if (kvArr.length > 1) {
        result.put(decodeUrl(kvArr(0)), kvArr(1))
      }else {
        result.put(decodeUrl(kvArr(0)), "")
      }
    })
    return result.toMap
  }

  def decodeUrl(str:String):String = {
    return URLDecoder.decode(str, StandardCharsets.UTF_8.name())
  }
}
