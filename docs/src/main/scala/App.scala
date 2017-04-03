package io.github.fand.clonepool.docs
import scala.scalajs.js.JSApp
import org.scalajs.dom.document
import japgolly.scalajs.react._, vdom.html_<^._
import scalacss.Defaults._
import scalacss.ScalaCssReact._
import scalacss._
import scala.scalajs.js

object MyApp extends JSApp {
  def dedent(str: String) = {
    val lines = str.split("\n").tail.init
    val indent = "^(\\s*).*$".r
    val level = lines.map(line => line match {
      case indent(s) => s.length
      case _ => 0
    }).min
    lines.map(_.substring(level)).mkString("\n")
  }

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
      Snippet(dedent("""
        $ clonepool
        $ clonepool my-branch
        $ clonepool my-repo
        $ clonepool my-repo my-branch
      """))
    )
  )

  val shell = ScalaComponent.static("Shell")(
    <.section(
      <.h1("Clone in the Shell"),
      <.p(dedent("""
        Add following scripts to your ~/.bashrc
      """)),
      Snippet(dedent("""
        function go_clone() {
            if [[ $1 == '' ]]; then
                cd $(clonepool | peco)
            else
                cd $(clonepool $1)
            fi
        }
      """))
    )
  )

  val background = ScalaComponent.static("Background")(
    <.div(
      Styles.background("background"),
      <.div(Styles.background("mask")),
      (1 to 100).toVdomArray(i => <.img(
        Styles.cloneImage(i),
        ^.src := "./images/clone.png",
        ^.className := s"Clone-$i",
        ^.style := js.Dynamic.literal(
          "transform" -> s"rotate(${Math.random * 40 - 20}deg)"
        )
      ))
    )
  )

  val App = ScalaComponent.static("App")(
    <.div(
      Styles.app,
      <.div(
        header(),
        install(),
        usage(),
        shell(),
        background(),
        Styles.container
      )
    )
  )

  override def main(): Unit = {
    Styles.addToDocument()
    App().renderIntoDOM(document.body)
  }
}
