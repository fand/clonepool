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
      (1 to 100).toVdomArray(i => <.img(
        BackgroundStyle.cloneImage(i),
        ^.src := "./images/clone.png",
        ^.className := s"Clone-$i",
        ^.style := js.Dynamic.literal(
          "transform" -> s"rotate(${Math.random * 40 - 20}deg)"
        )
      ))
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
      zIndex(-3),
      overflow.hidden
    ),
    "mask" -> style(
      position.fixed,
      top(0.px),
      left(0.px),
      width(100%%),
      height(100%%),
      backgroundColor(rgba(0, 0, 0, 0.1)),
      zIndex(-1)
    )
  )

  def kf(i: Int) = {
    val seed = Math.random * 110 - 5
    def wiggle() = left((Math.random * 5 + seed) %%)
    def wiggleMobile() = media.maxWidth(640 px)(
      left((Math.random * 10 + seed) %%)
    )
    keyframes(
      (0 %%)   -> keyframe(wiggle(), wiggleMobile(), top(110 %%)),
      (11 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (19 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (29 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (41 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (59 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (65 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (73 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (87 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (91 %%)  -> keyframe(wiggle(), wiggleMobile()),
      (100 %%) -> keyframe(wiggle(), wiggleMobile(), top(-10 %%))
    )
  }

  val cloneImage = styleF.int(0 to 100)(i => styleS(
    position.absolute,
    animationName(kf(i)),
    animationDuration((40 + Math.random * 20) seconds),
    media.maxWidth(640 px)(
      animationDuration((60 + Math.random * 20) seconds)
    ),
    animationDelay(Math.random * -60 seconds),
    animationIterationCount.infinite,
    animationTimingFunction.linear,
    zIndex(-1),
    opacity(Math.random * 0.2)
  ))
}
