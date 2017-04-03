package io.github.fand.clonepool.docs
import scalacss.Defaults._
import scala.concurrent.duration._

object Styles extends StyleSheet.Inline {
  import dsl._

  val app = style(
    position.absolute,
    minHeight(100%%),
    width(100%%),
    color(c"#EFF")
  )

  val container = style(
    margin(0 px, auto),
    padding(0 px, 10 px),
    width(100%%),
    maxWidth(960 px)
  )

  val code = style(
    display.block,
    padding(10 px),
    backgroundColor(rgba(0, 0, 30, 0.4)),
    color(c"#EFF"),
    lineHeight(1.6 em)
  )

  val background = Map(
    "background" -> style(
      position.absolute,
      top(0.px),
      left(0.px),
      width(100%%),
      height(100%%),
      backgroundColor(c"#069"),
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
    keyframes(
      (0 %%)   -> keyframe(wiggle(), top(110 %%)),
      (11 %%)  -> keyframe(wiggle()),
      (19 %%)  -> keyframe(wiggle()),
      (29 %%)  -> keyframe(wiggle()),
      (41 %%)  -> keyframe(wiggle()),
      (59 %%)  -> keyframe(wiggle()),
      (65 %%)  -> keyframe(wiggle()),
      (73 %%)  -> keyframe(wiggle()),
      (87 %%)  -> keyframe(wiggle()),
      (91 %%)  -> keyframe(wiggle()),
      (100 %%) -> keyframe(wiggle(), top(-10 %%))
    )
  }

  val cloneImage = styleF.int(0 to 100)(i => styleS(
    position.absolute,
    animationName(kf(i)),
    animationDuration((40 + Math.random * 20) seconds),
    animationDelay(Math.random * -60 seconds),
    animationIterationCount.infinite,
    animationTimingFunction.linear,
    zIndex(-1),
    opacity(Math.random * 0.2)
  ))
}
