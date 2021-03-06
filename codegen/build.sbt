import com.nitro.build._

import PublishHelpers._

// GAV coordinates
lazy val projectName = "avro-codegen-compiler"
name := projectName
version := semver.toString

// dependencies & resolvers
libraryDependencies ++= Seq(
  "org.scala-lang"             % "scala-reflect" % scalaVersion.value,
  "org.apache.avro"            % "avro"          % "1.7.7"
)
resolvers ++= Seq(
  "Typesafe Releases Repository - common" at "http://repo.typesafe.com/typesafe/releases/"
)

// compile & runtime settings
scalaVersion := "2.10.5"
CompileScalaJava.pluginSettings(devConfig)
javaOptions := jvmOpts

// publishing
pubSettings
