import sbt._

object Dependencies {

  object Versions {
    val fs2      = "3.9.4"
    val log4cats = "2.6.0"
    val http4s   = "0.23.24"
    val circe    = "0.14.6"
    val sttp     = "3.9.3"

    val scalaTest = "3.2.18"
    val mockito   = "3.2.18.0"
  }

  object Libraries {
    val fs2      = "co.fs2"        %% "fs2-core"       % Versions.fs2
    val log4cats = "org.typelevel" %% "log4cats-slf4j" % Versions.log4cats

    object http4s {
      val dsl         = "org.http4s" %% "http4s-dsl"          % Versions.http4s
      val emberServer = "org.http4s" %% "http4s-ember-server" % Versions.http4s
    }

    object circe {
      val parser = "io.circe" %% "circe-parser" % Versions.circe
    }

    object sttp {
      val core        = "com.softwaremill.sttp.client3" %% "core" % Versions.sttp
      val catsBackend = "com.softwaremill.sttp.client3" %% "fs2"  % Versions.sttp
    }

    val scalaTest = "org.scalatest"     %% "scalatest"    % Versions.scalaTest
    val mockito   = "org.scalatestplus" %% "mockito-4-11" % Versions.mockito
  }

  val test = Seq(
    Libraries.scalaTest,
    Libraries.mockito
  )

  val syntax = Seq(
    Libraries.scalaTest % Test,
    Libraries.mockito   % Test
  )

  val cats = Seq(
    Libraries.fs2,
    Libraries.log4cats
  )

  val http4s = Seq(
    Libraries.http4s.emberServer
  )

  val catsTest = Seq(
    Libraries.fs2
  )

  val http4sTest = Seq(
    Libraries.http4s.dsl,
    Libraries.circe.parser
  )

  val sttpTest = Seq(
    Libraries.sttp.core,
    Libraries.sttp.catsBackend,
  )
}
