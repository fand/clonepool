package io.github.fand.clonepool.docs
import scalacss.Defaults._

object Styles extends StyleSheet.Standalone {
  import dsl._

  "body" - (
    backgroundColor(c"#EFF"),
    color(c"#567")
  )

  "pre code" - (
    display.block,
    backgroundColor(c"#567"),
    color(c"#EFF")
  )
}
