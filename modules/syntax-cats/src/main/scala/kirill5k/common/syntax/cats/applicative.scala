package kirill5k.common.syntax.cats

import cats.Applicative
import cats.syntax.functor.*

object applicative:
  extension [F[_], A](fo: F[Option[A]])
    def mapOpt[B](f: A => B)(using F: Applicative[F]): F[Option[B]] =
      fo.map(_.map(f))

  extension [F[_], A](fo: F[Iterable[A]])
    def mapList[B](f: A => B)(using F: Applicative[F]): F[List[B]] =
      fo.map(_.map(f).toList)

  extension [F[_]](F: Applicative[F])
    def ifTrueOrElse[A](cond: Boolean)(ifTrue: => F[A], ifFalse: => F[A]): F[A] =
      if (cond) ifTrue else ifFalse
    def whenNonEmpty[A](opt: Option[A])(fa: A => F[Unit]): F[Unit] =
      opt match
        case Some(value) => fa(value)
        case None        => F.unit
