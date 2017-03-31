package io.github.fand.clonepool.cli
import io.github.fand.clonepool.util._
import org.backuity.ansi.AnsiFormatter.FormattedHelper

sealed trait Mode
case class HelpMode() extends Mode
case class VersionMode() extends Mode
case class ListMode() extends Mode
case class CheckoutMode(keywords: Seq[String]) extends Mode

object CLI {

  def init(args: Array[String]) = {
    if (args.exists(_.matches("(--help|-h)"))) {
      HelpMode
    }
    else if (args.exists(_.matches("(--version|-v)"))) {
      VersionMode
    }
    else if (args.size == 0) {
      ListMode
    }
    else {
      CheckoutMode(args.take(2))
    }
  }

  def help() = println(List(
    ansi"  ",
    ansi"  %bold{clonepool} - Git clone and branch manager",
    ansi"  ",
    ansi"  %underline{Usage}",
    ansi"  ",
    ansi"    $$ clonepool [options] <keywords>",
    ansi"  ",
    ansi"  %underline{Options}",
    ansi"  ",
    ansi"    %yellow{--help, -h}                     Show this help",
    ansi"    %yellow{--version, -v}                  Show the version of clonepool",
    ansi"  ",
    ansi"  %underline{Examples}",
    ansi"  ",
    ansi"    %cyan{$$ clonepool}                    List all clones",
    ansi"    %cyan{$$ clonepool my-branch}          Checkout to %bold{my-branch} of the branch of current directory",
    ansi"    %cyan{$$ clonepool my-repo}            Clone %bold{my-repo} and checkout to the default branch",
    ansi"    %cyan{$$ clonepool my-repo my-branch}  Clone %bold{my-repo} and checkout to %bold{my-branch}",
    ansi"  "
  ).mkString("\n"))

  def version() = println("clonepool ver 0.0.0")

}
