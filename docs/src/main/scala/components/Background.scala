package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Background {
  val component = ScalaComponent.static("Background")(
    <.div(
      BackgroundStyle.background("background"),
      <.div(BackgroundStyle.background("mask")),
      (1 to 100).toVdomArray(i =>
        Clone.component.withKey(s"clone-$i")(i)
      )
    )
  )
}

object BackgroundStyle extends StyleSheet.Inline {
  import dsl._
  val background = Map(
    "background" -> style(
      position.absolute,
      top(0.px),
      left(0.px),
      width(100%%),
      height(100%%),
      backgroundImage := "radial-gradient(farthest-side circle at center -400px, #5AF, #0DC 20%, #067 60%, #035)",
      overflow.hidden
    ),
    "mask" -> style(
      position.fixed,
      top(0.px),
      left(0.px),
      width(100%%),
      height(100%%),
      backgroundColor(rgba(0, 0, 0, 0.1)),
      zIndex(2),
      pointerEvents := "none"
    )
  )
}
