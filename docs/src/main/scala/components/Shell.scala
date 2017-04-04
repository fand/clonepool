package io.github.fand.clonepool.docs.components
import io.github.fand.clonepool.docs.Util._
import japgolly.scalajs.react._, vdom.html_<^._
import scala.concurrent.duration._
import scala.scalajs.js
import scalacss.Defaults._
import scalacss.ScalaCssReact._

object Shell {
  val component = ScalaComponent.static("Shell")(
    <.section(
      <.h2("Use with peco"),
      <.p(
        "Clonepool works well with !",
        Link.component("peco", "https://github.com/peco/peco"),
        "!",
        <.br,
        "Add these lines to your `~/.bashrc` (or `~/.zshrc`, etc)."
      ),
      Snippet.component(dedent("""
        function go_clone() {
            if [[ $1 == '' ]]; then
                cd $(clonepool | peco)
            else
                cd $(clonepool $1)
            fi
        }
      """))
    )
  )
}
