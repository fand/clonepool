package io.github.fand.clonepool

import org.scalatest._

class MainSpec extends FlatSpec with Matchers {
  "The Main object" should "say yo" in {
    Main.yo shouldEqual "yo"
  }
}
