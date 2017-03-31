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
      case CheckoutMode(keywords) => checkout(keywords)
    }
  }

  def list() =
    Pool.clones.foreach(println)

  def checkout(keywords: Seq[String]) = {
    val isInRepo = Repo.isDirInRepo(".")

    val (repo, pool, branch) = keywords match {
      case reponame +: branch +: Nil => checkoutRepoBranch(reponame, branch)
      case keyword +: Nil => if (isInRepo && !keyword.contains("/")) {
        checkoutBranch(keyword)
      }
      else {
        checkoutRepo(keyword)
      }
      case _ => throw new Exception("Invalid keywords")
    }

    if (!pool.doesPoolExist) {
      pool.createPool()
    }

    if (!pool.hasClone(branch)) {
      pool.createClone(repo.root, branch)
    }

    println(s"${pool.path}/$branch");
  }

  def checkoutRepoBranch(reponame: String, branch: String) = {
    val repo = Repo.fromName(reponame)
    val pool = Pool.fromRepo(repo)
    (repo, pool, branch)
  }

  def checkoutBranch(branch: String) = {
    val repo = Repo.fromDir(".")
    val pool = Pool.fromRepo(repo)
    (repo, pool, branch)
  }

  def checkoutRepo(reponame: String) = {
    val repo = Repo.fromName(reponame)
    val pool = Pool.fromRepo(repo)
    (repo, pool, repo.currentBranch)
  }

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
