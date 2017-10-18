package luoyong.restfuljetty.db

import scala.collection.mutable.ListBuffer

case class UserEntity(id:Long, name:String)

object User {

  val userList = ListBuffer[UserEntity]()

  def listAllUsers():List[UserEntity] = {
    return userList.toList
  }
}
