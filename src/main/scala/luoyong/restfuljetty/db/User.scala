package luoyong.restfuljetty.db

import scala.collection.mutable.ListBuffer

case class UserEntity(id:Long, name:String, fullName:String, description:String)

object User {

  val userList = ListBuffer[UserEntity](UserEntity(1, "Angelica", "Ramos", "System Architect"),
    UserEntity(1, "Ashton", "Cox", "Technical Author"))

  def listAllUsers():List[UserEntity] = {
    return userList.toList
  }
}
