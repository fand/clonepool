package io.github.fand.clonepool.docs.components
import io.github.fand.clonepool.docs.Util._
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Usage {
  val component = ScalaComponent.static("Usage")(
    <.section(
      <.h2("Usage"),
      Snippet.component(dedent("""
        $ clonepool                    # List all clones
        $ clonepool my-branch          # Checkout to my-branch of the branch of current directory
        $ clonepool my-repo            # Clone my-repo and checkout to the default branch
        $ clonepool my-repo my-branch  # Clone my-repo and checkout to my-branch
      """))
    )
  )
}
