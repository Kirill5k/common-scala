package kirill5k.common.cats.syntax

import cats.MonadThrow

object monadthrow:
  extension [F[_], A](fo: F[Option[A]])
    def unfoldOpt(ifEmpty: => Throwable)(using F: MonadThrow[F]): F[A] =
      F.flatMap(fo)(opt => F.fromOption[A](opt, ifEmpty))
