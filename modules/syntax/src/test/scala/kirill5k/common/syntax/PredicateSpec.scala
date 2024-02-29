package kirill5k.common.syntax

import org.scalatest.matchers.must.Matchers
import org.scalatest.wordspec.AnyWordSpec
import kirill5k.common.syntax.predicate.*

class PredicateSpec extends AnyWordSpec with Matchers {

  "Predicates syntax" should {
    "chain 2 predicates together" in {
      val isGreaterThanTwo = (i: Int) => i > 2
      val isSmallThanFive = (i: Int) => i < 5
      val isGreaterThanTwoAndSmallThanFive = isGreaterThanTwo and isSmallThanFive

      isGreaterThanTwoAndSmallThanFive(3) mustBe true
      isGreaterThanTwoAndSmallThanFive(1) mustBe false
      isGreaterThanTwoAndSmallThanFive(6) mustBe false
    }
  }
}
