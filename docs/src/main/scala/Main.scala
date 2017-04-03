package io.github.fand.clonepool.docs
import io.github.fand.clonepool.docs.components._
import org.scalajs.dom.document
import scala.scalajs.js.JSApp
import scalacss.Defaults._

object Main extends JSApp {
  override def main(): Unit = {
    BackgroundStyle.addToDocument()
    SnippetStyle.addToDocument()
    AppStyle.addToDocument()

    App.component().renderIntoDOM(document.body)
  }
}
