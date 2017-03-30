package io.github.fand.clonepool
import io.github.fand.clonepool.lib._
import io.github.fand.clonepool.cli._
import scala.util.control.Exception._
import scala.sys.process.{Process, ProcessLogger}

object Main {

  def main(args: Array[String]) = {
    initialize()
    CLI.init(args) match {
      case HelpMode => CLI.help()
      case VersionMode => CLI.version()
      case ListMode => list()
      case CheckoutMode(Nil) => CLI.help()
      case CheckoutMode(keywords) => checkout(keywords)
    }
  }

  def list() = {
    val repo = Repo.fromDir(".")
    println(repo.branches)
  }

  def checkout(keywords: Seq[String]) = {
    val repo = Repo.fromDir(".")
    val pool = Pool(repo)
    println("gonna checkout")
    println(pool)
    println(pool.cloneDst)
    println(pool.shouldClone)
    println(pool.doesPoolExist)
  }

  // val repo = Repo.fromDir(".")
  // // val repo = Repo.fromName("fand")
  // println(repo.branches)
  // println(repo.currentBranch)

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
