package io.github.fand.clonepool.docs
import scalacss.Defaults._

object Styles extends StyleSheet.Inline {
  import dsl._

  val app = style(
    position.absolute,
    minHeight(100%%),
    width(100%%),
    backgroundColor(c"#EFF"),
    color(c"#567")
  )

  val code = style(
    display.block,
    backgroundColor(c"#567"),
    color(c"#EFF")
  )
}
