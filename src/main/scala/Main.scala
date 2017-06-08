import java.io.File
import java.util.logging.Logger

import org.eclipse.jetty.server.Server
import org.eclipse.jetty.webapp.WebAppContext

/**
  *
  */
object Main {

  val logger = Logger.getLogger(Main.getClass.getCanonicalName)

  def main(args:Array[String]):Unit = {

    try {
      println(new File("").getAbsolutePath)

      val webAppContext = new WebAppContext("web", "/web")
      webAppContext.setExtractWAR(true)

      val server = new Server(8080)
      server.setHandler(webAppContext)
      server.start()
      server.join()

    }catch {
      case t:Throwable => {

      }
    }
  }
}
