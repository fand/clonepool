package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Footer {
  val component = ScalaComponent.static("Footer")(
    <.footer(
      FooterStyle.footer,
      <.span("GitHub : ",
        <.a(
          ^.href := "https://github.com/fand/clonepool/",
          "https://github.com/fand/clonepool/"
        )
      ),
      <.span("Twitter : ",
        <.a(
          ^.href := "https://twitter.com/amagitakayosi/",
          "https://twitter.com/amagitakayosi/"
        )
      )
    )
  )
}

object FooterStyle extends StyleSheet.Inline {
  import dsl._

  val footer = style(
    textAlign.center,
    padding(20 px)
  )
}
