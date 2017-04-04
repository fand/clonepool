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
      """)),
      <.p(
        "Assume that you are gonna create a pull-request to ",
        Link.component(
          "Play framework",
          "https://github.com/playframework/playframework"
        ),
        ".",
        <.br,
        "Run this command in your terminal."
      ),
      Snippet.component(dedent("""
        $ clonepool playframework/playframework my-branch
      """)),
      <.p(
        "This is almost equivalent to running following commands."
      ),
      Snippet.component(dedent("""
        $ ghq get playframework/playframework
        $ cp -r $(ghq root)/github.com/playframework/playframework ~/.clonepool/github.com/playframework/playframework/my-branch
        $ echo ~/.clonepool/github.com/playframework/playframework/my-branch
      """)),
      <.p(
        "Clonepool clones the repository using ",
        Link.component("ghq", "https://github.com/motemen/ghq"),
        ".",
        <.br,
        "So you can develop in any branches without affecting class caches!"
      ),
      <.p(
        "If you are already in a Git repository, you can omit the repository name:"
      ),
      Snippet.component(dedent("""
        $ clonepool my-branch
      """)),
      <.p(
        "Clonepool doesn't have a command to clean the clones.",
        <.br,
        "When you finished the branch, remove the clone by hand."
      ),
      Snippet.component(dedent("""
        $ rm $(clonepool playframework/playframework my-branch)
      """))
    )
  )
}
