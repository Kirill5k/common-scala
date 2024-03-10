import sbt._

object Dependencies {

  object Versions {
    val fs2      = "3.9.4"
    val log4cats = "2.6.0"
    val http4s   = "0.23.24"

    val scalaTest = "3.2.18"
    val mockito   = "3.2.18.0"
  }

  object Libraries {
    val fs2      = "co.fs2"        %% "fs2-core"       % Versions.fs2
    val log4cats = "org.typelevel" %% "log4cats-slf4j" % Versions.log4cats

    object http4s {
      val core        = "org.http4s" %% "http4s-core"         % Versions.http4s
      val dsl         = "org.http4s" %% "http4s-dsl"          % Versions.http4s
      val server      = "org.http4s" %% "http4s-server"       % Versions.http4s
      val emberServer = "org.http4s" %% "http4s-ember-server" % Versions.http4s

      val all = Seq(core, dsl, server, emberServer)
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
}
