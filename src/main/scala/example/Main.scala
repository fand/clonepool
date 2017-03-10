package io.github.fand.clonepool
import io.github.fand.clonepool.lib._
import io.github.fand.clonepool.cli._
import scala.util.control.Exception._
import scala.sys.process.{Process, ProcessLogger}

object Main extends App {
  initialize()

  // Parse CLI
  val config = CLI.init(args)
  // config.mode match {
  //   case "checkout" => println(s"checkout to ${config.branch}")
  //   case "list" => println("list")
  // }

  val repo = Repo.fromDir(".")
  // val repo = Repo.fromName("fand")
  println(repo.branches)
  println(repo.currentBranch)

  // git.showBranches()
  // println(git.remoteOrigin)

  private def exitcode(cmd: String): Int =
    Process(cmd) ! ProcessLogger(str => ())

  private def initialize() = {
    if (exitcode("which ghq") != 0) {
      throw new Exception("ghq not found")
    }
    if (exitcode("which peco") != 0) {
      throw new Exception("peco not found")
    }
  }

}
