package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Clone {
  def component = ScalaComponent.builder[Int]("Clone")
    .render_P(i =>
      <.img(
        CloneStyle.cloneImage(i),
        ^.src := "./images/clone.png",
        ^.className := s"Clone-$i",
        ^.style := js.Dynamic.literal(
          "transform" -> s"rotate(${Math.random * 40 - 20}deg)"
        )
      )
    )
    .build
}

object CloneStyle extends StyleSheet.Inline {
  import dsl._

  def kf = {
    val seed = Math.random * 110 - 5

    def wiggle = left((Math.random * 5 + seed) %%)

    def wiggleMobile = media.maxWidth(640 px)(
      left((Math.random * 10 + seed) %%)
    )

    keyframes(
      (0 %%)   -> keyframe(wiggle, wiggleMobile, top(110 %%)),
      (11 %%)  -> keyframe(wiggle, wiggleMobile),
      (19 %%)  -> keyframe(wiggle, wiggleMobile),
      (29 %%)  -> keyframe(wiggle, wiggleMobile),
      (41 %%)  -> keyframe(wiggle, wiggleMobile),
      (59 %%)  -> keyframe(wiggle, wiggleMobile),
      (65 %%)  -> keyframe(wiggle, wiggleMobile),
      (73 %%)  -> keyframe(wiggle, wiggleMobile),
      (87 %%)  -> keyframe(wiggle, wiggleMobile),
      (91 %%)  -> keyframe(wiggle, wiggleMobile),
      (100 %%) -> keyframe(wiggle, wiggleMobile, top(-10 %%))
    )
  }

  val cloneImage = styleF.int(0 to 100)(i => styleS(
    position.absolute,
    animationName(kf),
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
