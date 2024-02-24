import sbtghactions.JavaSpec

ThisBuild / scalaVersion                        := "3.3.1"
ThisBuild / organization                        := "io.github.kirill5k"
ThisBuild / githubWorkflowPublishTargetBranches := Nil
ThisBuild / githubWorkflowJavaVersions          := Seq(JavaSpec.temurin("20"))
ThisBuild / scalacOptions ++= Seq("-Wunused:all")

val noPublish = Seq(
  publish         := {},
  publishLocal    := {},
  publishArtifact := false,
  publish / skip  := true
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
