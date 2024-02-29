package kirill5k.common.syntax

object predicate:
  extension [A](predicate: A => Boolean)
    def and(anotherPredicate: A => Boolean): A => Boolean =
      a => predicate(a) && anotherPredicate(a)
