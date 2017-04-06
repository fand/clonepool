package io.github.fand.clonepool.docs
import io.github.fand.clonepool.docs.components.App
import org.scalajs.dom.document
import scala.util.Try
import scala.scalajs.js.JSApp
import scala.scalajs.js.Dynamic.global

object Main extends JSApp {
  val userAgent: String =
    Try(global.navigator.userAgent.asInstanceOf[String]) getOrElse "Unknown"
  val isChrome  = userAgent contains "Chrome"

  override def main(): Unit = {
    Styles.addToDocument()
    App.component().renderIntoDOM(document.querySelector("#app"))

    if (isChrome) {
      global.console.log(
        "%c+%c  < Click us!",
        "font-size: 1px; padding: 26px 23px; line-height: 64px; background: url('https://fand.github.io/clonepool/images/clone.png'); background-size: 51px 64px; color: transparent",
        ""
      )
    }
    else {
      global.console.log("Click Octocats!")
    }
  }
}
