package luoyong.restfuljetty.db

import scala.collection.mutable.ListBuffer

case class UserEntity(id:Long, username:String, password:String, name:String, info:String)

object User {

  val userList = ListBuffer[UserEntity](UserEntity(1, "Angelica", "", "Ramos", "System Architect"),
    UserEntity(2, "Ashton", "", "Cox", "<i>Technical Author</i>"))

  def listAllUsers():List[UserEntity] = {
    return userList.toList
  }

  def add(user:UserEntity) = {
    userList += user
  }

  def delete(user:UserEntity) = {
    userList -= user
  }
}
