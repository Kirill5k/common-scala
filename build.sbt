import sbtghactions.JavaSpec

inThisBuild(
  List(
    scalaVersion                        := "3.3.1",
    organization                        := "io.github.kirill5k",
    homepage                            := Some(url("https://github.com/kirill5k/common-scala")),
    licenses                            := List("Apache-2.0" -> url("http://www.apache.org/licenses/LICENSE-2.0")),
    developers                          := List(Developer("kirill5k", "Kirill", "immotional@aol.com", url("https://github.com/kirill5k"))),
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
    libraryDependencies ++= Dependencies.syntax
  )

val test = project
  .in(file("modules/test"))
  .settings(
    name       := "common-test",
    moduleName := "common-test",
    libraryDependencies ++= Dependencies.test
  )

val catsTest = project
  .in(file("modules/test-cats"))
  .dependsOn(test)
  .settings(
    name       := "common-test-cats",
    moduleName := "common-test-cats",
    libraryDependencies ++= Dependencies.testCats
  )

val root = project
  .in(file("."))
  .settings(noPublish)
  .settings(
    name := "common-scala"
  )
  .aggregate(test, catsTest, syntax)
