package io.github.fand.clonepool.docs
import scalacss.Defaults._

object Styles extends StyleSheet.Inline {
  import dsl._

  val app = style(
    position.absolute,
    minHeight(100%%),
    width(100%%),
    color(c"#EFF")
  )

  val code = style(
    display.block,
    backgroundColor(c"#567"),
    color(c"#EFF")
  )

  val background = Map(
    "background" -> style(
      position.absolute,
      top(0.px),
      left(0.px),
      width(100%%),
      height(100%%),
      backgroundColor(c"#069"),
      zIndex(-3)
    ),
    "mask" -> style(
      position.fixed,
      top(0.px),
      left(0.px),
      width(100%%),
      height(100%%),
      backgroundColor(rgba(0, 0, 0, 0.1)),
      zIndex(-1)
    )
  )

  val cloneImage = styleF.int(0 to 100)(i => styleS(
    position.absolute,
    top((Math.random * 80 + 10).%%),
    left((Math.random * 80 + 10).%%),
    zIndex(-1),
    opacity(0.3)
  ))
}
