package io.github.fand.clonepool.cli
import io.github.fand.clonepool.util._

case class CLIConfig(
  mode: String = "checkout",
  branch: String = ""
)

object CLI {
  def initScopt(args: Seq[String]): CLIConfig = {
    val parser = new scopt.OptionParser[CLIConfig]("clonepool") {
      head("clonepool", "0.0.0")
      help("help").text("prints this usage text")

      cmd("checkout")
        .action((_, c) => c.copy(mode = "checkout"))
        .text("checkout")
        .children(
          arg[String]("<branch>")
            .optional()
            .action((x, c) => c.copy(branch = x))
            .text("checkout to a branch. clone it if needed")
        )

      cmd("list")
        .action((_, c) => c.copy(mode = "list"))
        .text("list available clones")
    }

    parser.parse(args, CLIConfig()).getOrThrow(new Exception("Invalid arguments"))
  }
}
