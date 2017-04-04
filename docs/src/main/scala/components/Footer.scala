package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Footer {
  val component = ScalaComponent.static("Footer")(
    <.footer(
      FooterStyle.footer,
      <.span("Written by ",
        <.a(
          FooterStyle.link,
          ^.href := "https://twitter.com/amagitakayosi/",
          "@amagitakayosi"
        )
      )
    )
  )
}

object FooterStyle extends StyleSheet.Inline {
  import dsl._

  val footer = style(
    textAlign.center,
    padding(40 px, 0 px, 60 px)
  )

  val link = style(
    color(c"#EFF")
  )
}
