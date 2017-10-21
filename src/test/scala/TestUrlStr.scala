import java.net.{URLDecoder, URLEncoder}
import java.nio.charset.StandardCharsets

import org.scalatest.FunSuite

class TestUrlStr extends FunSuite {
  test("test1") {
    val str = "columns%5B0%5D%5Bsearch%5D%5Bvalue%5D"
    val result = URLDecoder.decode(str, StandardCharsets.UTF_8.name())
    println(result)
  }
}
