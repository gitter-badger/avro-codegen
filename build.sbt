
organization in ThisBuild := "com.gonitro"

scalaVersion in ThisBuild := "2.11.7"

lazy val root =
  project.in(file("."))
    .settings {
      publishArtifact := false
      publish := { } // prevents us from publishing a root artifact (do not remove!!)
      publishLocal := { }
    }
    .aggregate(runtime, codegen)

lazy val runtime = project.in(file("runtime"))

lazy val codegen = project.in(file("codegen"))

lazy val ShortTest = config("short") extend Test

def genVersionFile(out: File, runtimeVersion: String, codegenVersion: String): File = {
  out.mkdirs()
  val f = out / "Version.scala"
  val w = new java.io.FileOutputStream(f)
  w.write(s"""|// Generated by Avro Codegen's build.sbt.
              |
              |package com.nitro.scalaAvro.runtime
              |
              |object Version {
              |  val sbtPluginVersion = "$codegenVersion"
              |  val avroCodegenVersion = "$runtimeVersion"
              |}
              |""".stripMargin.getBytes("UTF-8"))
  w.close()
  f
}

val createVersionFile = TaskKey[Unit](
  "create-version-file", "Creates a file with the project version to be used by e2e."
)

createVersionFile <<= (streams, baseDirectory, version in codegen, version in runtime) map {
  (streams, baseDirectory, codegenVersion, runtimeVersion) =>
    val f1 = genVersionFile(baseDirectory / "e2e/project/project", runtimeVersion, codegenVersion)
    streams.log.info(s"Created $f1")
    val f2 = genVersionFile(baseDirectory / "e2e/project/",  runtimeVersion, codegenVersion)
    streams.log.info(s"Created $f2")
}
