package io.github.fand.clonepool.docs
import io.github.fand.clonepool.docs.components._
import org.scalajs.dom.document
import scalacss.Defaults._

object Styles extends StyleSheet.Standalone {
  import dsl._

  "body" - (
    fontFamily := "arial, sans-serif"
  )

  "p" - (
    lineHeight(1.6 em)
  )

  def addToDocument(): Unit = {
    val s = document.createElement("style")
    s.innerHTML = Styles.render
    document.querySelector("head").appendChild(s)

    HeaderStyle.addToDocument()
    BackgroundStyle.addToDocument()
    SnippetStyle.addToDocument()
    AppStyle.addToDocument()
    FooterStyle.addToDocument()
    LinkStyle.addToDocument()
  }
}
