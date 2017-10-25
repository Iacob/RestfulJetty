name := "RestfulJetty"

version := "1.0"

scalaVersion := "2.12.3"

val workaround = {
  sys.props += "packaging.type" -> "jar"
  ()
}

libraryDependencies += "org.eclipse.jetty" % "jetty-servlet" % "9.4.7.v20170914"
libraryDependencies += "org.eclipse.jetty" % "jetty-server" % "9.4.7.v20170914"
libraryDependencies += "org.eclipse.jetty" % "jetty-webapp" % "9.4.7.v20170914"

libraryDependencies += "org.glassfish.jersey.core" % "jersey-server" % "2.26"
libraryDependencies += "org.glassfish.jersey.containers" % "jersey-container-servlet" % "2.26"
libraryDependencies += "org.glassfish.jersey.inject" % "jersey-hk2" % "2.26"

libraryDependencies += "net.liftweb" % "lift-json_2.12" % "3.1.1"

libraryDependencies += "commons-io" % "commons-io" % "2.5"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % Test

libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.6"
