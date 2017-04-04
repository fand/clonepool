package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Header {
  val component = ScalaComponent.static("Header")(
    <.header(
      HeaderStyle.header,
      <.img(^.src := "images/logo.png", HeaderStyle.logo),
      <.h1("CLONEPOOL"),
      <.a(
        ^.href := "https://github.com/fand/clonepool",
        <.img(
          ^.src := "https://img.shields.io/github/stars/fand/clonepool.svg?style=social&label=Star"
        )
      ),
      <.p(
        HeaderStyle.description,
        "Clonepool is a CLI utility to manage copies of git repositories",
        <.br,
        "Created to reduce compilation times on Scala project."
      )
    )
  )
}

object HeaderStyle extends StyleSheet.Inline {
  import dsl._

  val header = style(
    textAlign.center
  )

  val logo = style(
    display.block,
    margin(20 px, auto),
    width(320 px),
    maxWidth(40%%)
  )

  val description = style(
    margin(10 px, 0 px)
  )
}
