package kirill5k.common.cats.test

import cats.effect.IO
import cats.effect.unsafe.IORuntime
import org.scalatest.{Assertion, EitherValues}
import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AsyncWordSpec

import scala.concurrent.Future

trait IOWordSpec extends AsyncWordSpec with Matchers with IOMockitoMatchers with EitherValues {

  extension [A](io: IO[A])
    def assertVoid: Future[Assertion] = asserting(_ mustBe ())
    def assertThrows(error: Throwable): Future[Assertion] =
      io.attempt.asserting(_ mustBe Left(error))
    def asserting(f: A => Assertion): Future[Assertion] =
      io.map(f).unsafeToFuture()(IORuntime.global)
}
