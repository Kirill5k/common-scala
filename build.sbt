import sbtghactions.JavaSpec
import org.typelevel.scalacoptions.ScalacOptions

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
    libraryDependencies ++= Dependencies.syntax,
    Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
  )

val test = project
  .in(file("modules/test"))
  .settings(
    name       := "common-test",
    moduleName := "common-test",
    libraryDependencies ++= Dependencies.test,
    Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
  )

val catsTest = project
  .in(file("modules/cats-test"))
  .dependsOn(test)
  .settings(
    name       := "common-cats-test",
    moduleName := "common-cats-test",
    libraryDependencies ++= Dependencies.catsTest,
    Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
  )

val cats = project
  .in(file("modules/cats"))
  .dependsOn(catsTest % "test->compile")
  .settings(
    name       := "common-cats",
    moduleName := "common-cats",
    libraryDependencies ++= Dependencies.cats,
    Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
  )

val http4s = project
  .in(file("modules/http4s"))
  .settings(
    name       := "common-http4s",
    moduleName := "common-http4s",
    libraryDependencies ++= Dependencies.http4s,
    Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
  )

val http4sTest = project
  .in(file("modules/http4s-test"))
  .dependsOn(catsTest)
  .settings(
    name       := "common-http4s-test",
    moduleName := "common-http4s-test",
    libraryDependencies ++= Dependencies.http4sTest,
    Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
  )

val sttpTest = project
  .in(file("modules/sttp-test"))
  .dependsOn(catsTest)
  .settings(
    name       := "common-sttp-test",
    moduleName := "common-sttp-test",
    libraryDependencies ++= Dependencies.sttpTest,
    Test / tpolecatExcludeOptions += ScalacOptions.warnNonUnitStatement
  )

val root = project
  .in(file("."))
  .settings(noPublish)
  .settings(
    name := "common-scala"
  )
  .aggregate(test, catsTest, http4sTest, sttpTest, cats, syntax, http4s)
