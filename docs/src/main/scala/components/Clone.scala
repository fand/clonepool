package io.github.fand.clonepool.docs.components
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

case class CloneState(count: Int = 0) {}

class CloneBackend($: BackendScope[Int, CloneState]) {
  def activate(): Callback =
    $.modState(s => s.copy(
      count = s.count + (if (Math.random > 0.5) 1 else -1)
    ))

  def render(i: Int, state: CloneState): VdomElement =
    <.img(
      CloneStyle.cloneImage(i),
      ^.src := "./images/clone.png",
      ^.className := s"Clone-$i",
      ^.style := js.Dynamic.literal(
        "transform" -> s"rotate(${Math.random * 40 - 20 + state.count * 360}deg)"
      ),
      ^.onClick --> activate()
    )
}

object Clone {
  def component = ScalaComponent.builder[Int]("Clone")
    .initialState(new CloneState())
    .renderBackend[CloneBackend]
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
    opacity(Math.random * 0.2),
    transition := "0.3s"
  ))
}
