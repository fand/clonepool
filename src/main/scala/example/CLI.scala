package io.github.fand.clonepool.cli
import io.github.fand.clonepool.util._
import org.backuity.clist.{Cli, Command, opt, arg, args}

sealed trait Common { this: Command => // same as above
}

object Go extends Command(description = "Go to branch. Clone if needed") with Common {
  var target = arg[String]("The name of branch or repository")
}

object List extends Command(description = "checkout to branch") with Common

object CLI {
  def init(args: Array[String]) = {
    Cli.parse(args).withProgramName("clonepool").withCommands(Go, List) match {
      case Some(Go) => println(Go.target)
      case Some(List) => println("gonna list")
      case None => println("Strange args")
    }
  }
}
