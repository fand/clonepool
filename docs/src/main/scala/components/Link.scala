package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Link {
  val component = ScalaComponent.builder[Tuple2[String, String]]("Link")
    .render_P(t =>
      <.a(
        LinkStyle.link,
        ^.href := t._2,
        t._1
      )
    )
    .build
}

object LinkStyle extends StyleSheet.Inline {
  import dsl._
  val link = style(
    color(c"#EFF"),
    pointerEvents := "auto"
  )
}
