import sbt._

object Dependencies {

  object Versions {
    val fs2 = "3.9.4"

    val scalaTest = "3.2.18"
    val mockito   = "3.2.17.0"
  }

  object Libraries {
    val fs2 = "co.fs2" %% "fs2-core" % Versions.fs2

    val scalaTest = "org.scalatest"     %% "scalatest"    % Versions.scalaTest
    val mockito   = "org.scalatestplus" %% "mockito-4-11" % Versions.mockito
  }

  val test = Seq(
    Libraries.scalaTest,
    Libraries.mockito
  )

  val testCats = Seq(
    Libraries.fs2
  )

  val syntax = Seq(
    Libraries.scalaTest % Test,
    Libraries.mockito   % Test
  )

  val syntaxCats = Seq(
    Libraries.fs2
  )
}
