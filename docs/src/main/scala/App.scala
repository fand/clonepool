package io.github.fand.clonepool.docs
import scala.scalajs.js.JSApp
import org.scalajs.dom.document
import japgolly.scalajs.react._, vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss._

object MyApp extends JSApp {
  val Snippet = ScalaComponent.builder[String]("Snippet")
    .render_P(snippet =>
      <.pre(
        <.code(
          snippet,
          Styles.code
        )
      )
    )
    .build

  val header = ScalaComponent.static("Header")(
    <.header(
      <.h1("CLONEPOOL")
    )
  )

  val install = ScalaComponent.static("Install")(
    <.section(
      <.h1("Install"),
      Snippet("$ brew install clonepool")
    )
  )

  val usage = ScalaComponent.static("Usage")(
    <.section(
      <.h1("Usage"),
      Snippet("""
$ clonepool
$ clonepool my-branch
$ clonepool my-repo
$ clonepool my-repo my-branch
      """.trim())
    )
  )

  val App = ScalaComponent.static("App")(
    <.div(
      Styles.app,
      header(),
      install(),
      usage()
    )
  )

  override def main(): Unit = {
    Styles.addToDocument()
    App().renderIntoDOM(document.body)
  }
}
