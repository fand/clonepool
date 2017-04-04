package io.github.fand.clonepool.docs
import io.github.fand.clonepool.docs.components._
import org.scalajs.dom.document
import scala.scalajs.js.JSApp
import scalacss.Defaults._

object Main extends JSApp {
  override def main(): Unit = {
    HeaderStyle.addToDocument()
    BackgroundStyle.addToDocument()
    SnippetStyle.addToDocument()
    AppStyle.addToDocument()
    FooterStyle.addToDocument()
    LinkStyle.addToDocument()

    App.component().renderIntoDOM(document.body)
  }
}
