package luoyong.restfuljetty.rs

import java.net.URLDecoder
import java.nio.charset.StandardCharsets
import javax.servlet.http.HttpServletRequest
import javax.ws.rs.core.Context
import javax.ws.rs._

import luoyong.restfuljetty.db.UserEntity
import net.liftweb.json.{DefaultFormats, Serialization}
import org.apache.commons.io.IOUtils
import org.apache.commons.lang3.StringUtils

import collection.JavaConverters._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

case class UserEditEntity(id:Option[Long], username:String, password:String, name:String, info:String)

/**
  *
  */
@Path("/user")
class User {

  case class UserDataTable(draw:Long, recordsTotal:Long, recordsFiltered:Long, data:Array[Array[String]])

  case class UserWsResponse(ret:Long, code:String, msg:String, data:Any)

  case class UserFieldValidationMessage(field:String, code:String, msg:String)

  @Path("/dt-list")
  @Consumes(Array("application/x-www-form-urlencoded"))
  @Produces(Array("application/json"))
  @POST
  def list(@Context request:HttpServletRequest):String = {

    // Request content
    val req = IOUtils.toString(request.getInputStream, StandardCharsets.UTF_8)

    // extract form content from request
    val formKv = extractForm(req)

    val drawSeq:String = formKv.getOrElse("draw", "0")
    //println(drawSeq)

    val userDb = luoyong.restfuljetty.db.User

    val userList = ArrayBuffer[Array[String]]()
    val u1 = userDb.userList
    userDb.userList.foreach((userEntity) => {
      userList += Array(userEntity.username, userEntity.name, userEntity.info, "<input type=\"hidden\" value=\"" + userEntity.id + "\"><span data-toggle=\"modal\" data-target=\"#mm-edit-user-space\" onclick=\"Public.ui_action_show_edit_user_form(this, event);\">Edit</span>&nbsp;<span data-toggle=\"modal\" data-target=\"#mm-delete-user-dialog\" onclick=\"Public.ui_action_set_user_id_to_delete(this, event);\">Delete</span>")
    })

    implicit val formats = DefaultFormats
    val resultObj = UserDataTable(drawSeq.toLong, userDb.userList.size, userDb.userList.size, userList.toArray)
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

  @Path("/add")
  //@Consumes(Array("application/json"))
  @Produces(Array("application/json"))
  @POST
  def add(@Context request:HttpServletRequest):String = {
    val req = IOUtils.toString(request.getInputStream, StandardCharsets.UTF_8)
    implicit val formats = DefaultFormats
    val userInfo:UserEditEntity = Serialization.read[UserEditEntity](req)
    val userData:UserEntity = UserEntity(10, userInfo.username, userInfo.password, userInfo.name, userInfo.info)
    val userDb = luoyong.restfuljetty.db.User
    userDb.add(userData)

    val resultObj = UserWsResponse(0, "success", "", None)
    val result = Serialization.write(resultObj)
    println(userDb.listAllUsers())
    return result
  }

  @Path("/delete")
  //@Consumes(Array("application/json"))
  @Produces(Array("application/json"))
  @POST
  def delete(@Context request:HttpServletRequest):String = {
    val req = IOUtils.toString(request.getInputStream, StandardCharsets.UTF_8)
    val user_id = req.toLong

    val userDb = luoyong.restfuljetty.db.User
    val userToDeleteOpt:Option[UserEntity] = userDb.listAllUsers().find((userEntity) => {
      userEntity.id.longValue == user_id.longValue()
    })

    if (userToDeleteOpt.nonEmpty) {
      userDb.delete(userToDeleteOpt.get)
    }
    println(userDb.listAllUsers())

    return """ {"ret" : 0, "code" : "success"} """
  }

  @Path("/edit")
  //@Consumes(Array("application/json"))
  @Produces(Array("application/json"))
  @POST
  def editUserInfo(@Context request:HttpServletRequest):String = {
    val req = IOUtils.toString(request.getInputStream, StandardCharsets.UTF_8)
    implicit val formats = DefaultFormats
    val userInfo = Serialization.read[UserEntity](req)

    if (StringUtils.isBlank(userInfo.username)) {
      val resultObj = UserWsResponse(1, "validation_error", "", Array(UserFieldValidationMessage("username", "username_must_not_be_blank", "用户名不能为空")))
      val result = Serialization.write(resultObj)
      return result
    }

    val userDb = luoyong.restfuljetty.db.User
    val userToEditOpt:Option[UserEntity] = userDb.listAllUsers().find((userEntity) => {
      userEntity.id.longValue == userInfo.id.longValue()
    })

    if (userToEditOpt.nonEmpty) {
      val userToEdit = userToEditOpt.get
      userToEdit.username = userInfo.username
      userToEdit.password = userInfo.password
      userToEdit.name = userInfo.name
      userToEdit.info = userInfo.info

      println(userDb.listAllUsers())

      return """ {"ret" : 0, "code" : "success"} """
    }else {
      return """ {"ret" : 1, "code" : "user_not_found", "msg" : "user not found"} """
    }


  }

  @Path("/get/{id}")
  //@Consumes(Array("application/json"))
  @Produces(Array("application/json"))
  @GET
  def getUserInfo(@PathParam("id") user_id:Long):String = {
    val userDb = luoyong.restfuljetty.db.User
    val userInfoOpt:Option[UserEntity] = userDb.listAllUsers().find((userEntity) => {
      userEntity.id.longValue == user_id.longValue()
    })

    if (userInfoOpt.nonEmpty) {
      val resultObj = UserWsResponse(0, "success", "success", userInfoOpt.get)
      implicit val formats = DefaultFormats
      val result = Serialization.write(resultObj)
      return result
    }else {
      val resultObj = UserWsResponse(1, "user_not_found", "user not found", None)
      implicit val formats = DefaultFormats
      val result = Serialization.write(resultObj)
      return result
    }
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
