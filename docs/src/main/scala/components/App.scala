package io.github.fand.clonepool.docs.components
import io.github.fand.clonepool.docs.Util._
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object App {
  val component = ScalaComponent.static("App")(
    <.div(
      AppStyle.app,
      <.div(
        Header.component(),
        Install.component(),
        Usage.component(),
        Shell.component(),
        Background.component(),
        Footer.component(),
        AppStyle.container
      )
    )
  )
}

object AppStyle extends StyleSheet.Inline {
  import dsl._

  val app = style(
    position.absolute,
    minHeight(100%%),
    width(100%%),
    color(c"#EFF")
  )

  val container = style(
    margin(0 px, auto),
    padding(0 px, 10 px),
    width(100%%),
    maxWidth(960 px)
  )
}
