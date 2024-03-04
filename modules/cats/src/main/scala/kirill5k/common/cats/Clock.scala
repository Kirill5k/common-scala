package kirill5k.common.cats

import cats.effect.Temporal
import cats.syntax.functor.*

import java.time.Instant
import scala.concurrent.duration.*

trait Clock[F[_]]:
  def now: F[Instant]
  def durationBetweenNowAnd(otherTs: Instant): F[FiniteDuration]
  def sleep(duration: FiniteDuration): F[Unit]

final private class LiveClock[F[_]](using F: Temporal[F]) extends Clock[F] {
  override def now: F[Instant]                          = F.realTimeInstant
  override def sleep(duration: FiniteDuration): F[Unit] = F.sleep(duration)
  override def durationBetweenNowAnd(otherTs: Instant): F[FiniteDuration] =
    now.map(ts => math.abs(otherTs.toEpochMilli - ts.toEpochMilli).millis)
}

object Clock:
  given [F[_]: Temporal]: Clock[F] = Clock.make[F]

  def apply[F[_]](using C: Clock[F]): C.type = C
  def make[F[_]: Temporal]: Clock[F]         = LiveClock[F]()
