package kirill5k.common.syntax

object option:
  extension (O: Option.type)
    def flatWhen[A](cond: Boolean)(a: => Option[A]): Option[A] =
      if cond then a else None
