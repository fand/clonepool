package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Snippet {
  val component = ScalaComponent.builder[String]("Snippet")
    .render_P(snippet =>
      <.pre(
        <.code(
          snippet,
          SnippetStyle.code,
          ^.style := js.Dynamic.literal(
            "font-family" ->  "'Source Code Pro', monospace"
          )
        )
      )
    )
    .build
}

object SnippetStyle extends StyleSheet.Inline {
  import dsl._
  val code = style(
    display.block,
    padding(10 px),
    backgroundColor(rgba(0, 0, 30, 0.4)),
    color(c"#EFF"),
    lineHeight(1.6 em),
    overflow.scroll,
    pointerEvents := "auto"
  )
}
