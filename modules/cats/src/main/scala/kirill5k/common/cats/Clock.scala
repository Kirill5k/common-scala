package kirill5k.common.cats

import cats.Monad
import cats.effect.Temporal
import cats.syntax.functor.*

import java.time.Instant
import scala.concurrent.duration.*

trait Clock[F[_]]:
  def now: F[Instant]
  def durationBetweenNowAnd(otherTs: Instant): F[FiniteDuration]
  def sleep(duration: FiniteDuration): F[Unit]

  protected def durationBetween(ts: Instant, otherTs: Instant): FiniteDuration =
    math.abs(otherTs.toEpochMilli - ts.toEpochMilli).millis

final private class LiveClock[F[_]](using F: Temporal[F]) extends Clock[F] {
  override def now: F[Instant]                                            = F.realTimeInstant
  override def sleep(duration: FiniteDuration): F[Unit]                   = F.sleep(duration)
  override def durationBetweenNowAnd(otherTs: Instant): F[FiniteDuration] = now.map(ts => durationBetween(ts, otherTs))
}

final private class MockClock[F[_]: Monad](
    private var timestamp: Instant
)(using
    M: Monad[F]
) extends Clock[F] {
  override def now: F[Instant]                                            = M.pure(timestamp)
  override def durationBetweenNowAnd(otherTs: Instant): F[FiniteDuration] = M.pure(durationBetween(timestamp, otherTs))
  override def sleep(duration: FiniteDuration): F[Unit]                   = M.pure(set(timestamp.plusNanos(duration.toNanos)))

  def set(newTimestamp: Instant): Unit = {
    timestamp = newTimestamp
    ()
  }
}

object Clock {
  given [F[_]: Temporal]: Clock[F] = Clock.make[F]

  def apply[F[_]](using C: Clock[F]): C.type = C
  def make[F[_]: Temporal]: Clock[F]         = LiveClock[F]()

  def mock[F[_]: Monad](ts: Instant): MockClock[F] = MockClock[F](ts)
}
