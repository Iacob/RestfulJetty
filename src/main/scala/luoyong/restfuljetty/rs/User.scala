package luoyong.restfuljetty.rs

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs._

import net.liftweb.json.{DefaultFormats, Serialization}

import collection.JavaConverters._

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
  *
  */
@Path("/user")
class User {

  case class UserDataTable(draw:Long, recordsTotal:Long, recordsFiltered:Long, data:Array[Array[String]])

  @Path("/list")
  @Consumes(Array("application/x-www-form-urlencoded"))
  @Produces(Array("application/json"))
  @POST
  def list(@Context request:HttpServletRequest):String = {

    //println(columns)
    request.getParameterNames.asScala.foreach((name) => {println(name)})
    request.getParameterMap.asScala.foreach((kv) => {println(kv._1)})

    //val req = IOUtils.toString(request.getInputStream, StandardCharsets.UTF_8)
    //println(req)

    //val formKv = extractForm(req)
    //println(formKv.toString())

    val userDb = luoyong.restfuljetty.db.User

    val userList = ArrayBuffer[Array[String]]()
    val u1 = userDb.userList
    userDb.userList.foreach((userEntity) => {
      userList += Array(userEntity.name, userEntity.fullName, userEntity.description)
    })


    implicit val formats = DefaultFormats
    val resultObj = UserDataTable(1, userDb.userList.size, userDb.userList.size, userList.toArray)
    val result = Serialization.write(resultObj)
    return result

//    return """
//
//    {
//      "draw": 1,
//      "recordsTotal": 2,
//      "recordsFiltered": 2,
//      "data": [
//      [
//      "Angelica",
//      "Ramos",
//      "System Architect",
//      "London",
//      "9th Oct 09",
//      "$2,875"
//      ],
//      [
//      "Ashton",
//      "Cox",
//      "Technical Author",
//      "San Francisco",
//      "12th Jan 09",
//      "$4,800"
//      ]
//      ]
//    }
//      """
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
