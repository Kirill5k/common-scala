package kirill5k.common.test.cats

import cats.effect.IO
import kirill5k.common.test.MockitoMatchers
import org.mockito.stubbing.OngoingStubbing

trait IOMockitoMatchers extends MockitoMatchers {
  extension [A](stub: OngoingStubbing[IO[A]])
    def thenReturnIO(value: A): OngoingStubbing[IO[A]]           = stub.thenReturn(IO.pure(value))
    def thenRaiseError(error: Throwable): OngoingStubbing[IO[A]] = stub.thenReturn(IO.raiseError(error))

  extension (stub: OngoingStubbing[IO[Unit]]) def thenReturnUnit: OngoingStubbing[IO[Unit]] = stub.thenReturn(IO.unit)

  extension [A](stub: OngoingStubbing[fs2.Stream[IO, A]])
    def thenFailStream(error: Throwable) = stub.thenReturn(fs2.Stream.raiseError[IO](error))
    def thenStream(items: A*)            = stub.thenReturn(fs2.Stream.emits(items.toList))
    def thenStream(items: List[A])       = stub.thenReturn(fs2.Stream.emits(items))
    def thenReturnEmptyStream            = stub.thenReturn(fs2.Stream.empty)

  extension [A](stub: OngoingStubbing[IO[Option[A]]])
    def thenReturnNone: OngoingStubbing[IO[Option[A]]]           = stub.thenReturn(IO.none[A])
    def thenReturnSome(value: A): OngoingStubbing[IO[Option[A]]] = stub.thenReturn(IO.some(value))
}
