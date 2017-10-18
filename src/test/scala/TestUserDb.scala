import net.liftweb.json.{DefaultFormats, Serialization}
import org.scalatest.FunSuite

import scala.collection.mutable.ListBuffer

case class UserEntity(id:Long, name:String, password:Option[String])

class TestUserDb extends FunSuite {

  test("test1") {

    val userList = ListBuffer[UserEntity]()
    userList += UserEntity(1L, "user1", Some("pass1"))
    userList += UserEntity(2L, "user2", None)

    implicit val formats = DefaultFormats

    val result = Serialization.writePretty[List[UserEntity]](userList.toList)

    println(result)
  }

  test("test2") {

  }

}
