package kirill5k.common.syntax

object predicates:
  extension [A](predicate: A => Boolean)
    def and(anotherPredicate: A => Boolean): A => Boolean =
      a => predicate(a) && anotherPredicate(a)
