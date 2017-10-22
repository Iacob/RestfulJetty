import net.liftweb.json.{DefaultFormats, Serialization}
import org.scalatest.FunSuite

class TestJson extends FunSuite {

  test("test serialization") {
    val value1 = Map("key1" -> "value1", "key2" -> Array("value21", "value22"))

    implicit val formats = DefaultFormats
    val result = Serialization.write(value1)

    println(result)
  }

  case class Entity1(key1:String)
  test("test unmarshall") {
    val str = """ {"key1": "value1", "key2" : "value2"} """
    implicit val formats = DefaultFormats
    val e1:Entity1 = Serialization.read[Entity1](str)
    println(e1.key1)
  }
}
