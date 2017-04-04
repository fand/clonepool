package io.github.fand.clonepool.docs.components
import io.github.fand.clonepool.docs.Util._
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Install {
  val component = ScalaComponent.static("Install")(
    <.section(
      <.h2("Install"),
      Snippet.component("$ brew install clonepool")
    )
  )
}
