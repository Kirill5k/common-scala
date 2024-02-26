import sbtghactions.JavaSpec

inThisBuild(
  List(
    scalaVersion := "3.3.1",
    organization := "io.github.kirill5k",
    homepage     := Some(url("https://github.com/sbt/sbt-ci-release")),
    // Alternatively License.Apache2 see https://github.com/sbt/librarymanagement/blob/develop/core/src/main/scala/sbt/librarymanagement/License.scala
    licenses := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers := List(
      Developer(
        "olafurpg",
        "Ólafur Páll Geirsson",
        "olafurpg@gmail.com",
        url("https://geirsson.com")
      )
    ),
    githubWorkflowPublishTargetBranches := Nil,
    githubWorkflowJavaVersions          := Seq(JavaSpec.temurin("20")),
    scalacOptions ++= Seq("-Wunused:all")
  )
)

val noPublish = Seq(
  publish         := {},
  publishLocal    := {},
  publishArtifact := false,
  publish / skip  := true
)

val syntax = project
  .in(file("modules/syntax"))
  .settings(
    name       := "common-syntax",
    moduleName := "common-syntax",
    libraryDependencies ++= Dependencies.test
  )

val catsTest = project
  .in(file("modules/cats-test"))
  .settings(
    name       := "common-cats-test",
    moduleName := "common-cats-test",
    libraryDependencies ++= Dependencies.catsTest
  )

val root = project
  .in(file("."))
  .settings(noPublish)
  .settings(
    name := "common-scala"
  )
  .aggregate(catsTest)
