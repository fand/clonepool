package io.github.fand.clonepool.docs
import scala.scalajs.js.JSApp
import org.scalajs.dom.document
import japgolly.scalajs.react._, vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss._
import scala.scalajs.js

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

  val background = ScalaComponent.static("Background")(
    <.div(
      <.div(Styles.background("background")),
      <.div(Styles.background("mask")),
      (1 to 30).toVdomArray(i => <.img(
        Styles.cloneImage(i),
        ^.src := "./images/clone.png",
        ^.className := s"Clone-$i",
        ^.style := js.Dynamic.literal(
          "transform" -> s"rotate(${Math.random * Math.random * 60 - 30}deg)"
        )
      ))
    )
  )

  val App = ScalaComponent.static("App")(
    <.div(
      header(),
      install(),
      usage(),
      background(),
      Styles.app
    )
  )

  override def main(): Unit = {
    Styles.addToDocument()
    App().renderIntoDOM(document.body)
  }
}
