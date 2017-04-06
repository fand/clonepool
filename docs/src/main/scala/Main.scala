package io.github.fand.clonepool.docs
import io.github.fand.clonepool.docs.components.App
import org.scalajs.dom.document
import scala.scalajs.js.JSApp

object Main extends JSApp {
  override def main(): Unit = {
    Styles.addToDocument()
    App.component().renderIntoDOM(document.querySelector("#app"))
  }
}
