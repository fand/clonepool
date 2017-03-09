package io.github.fand.clonepool
import io.github.fand.clonepool.lib._
import scala.util.control.Exception._

object Main extends App {

  val git = GitWrapper.fromDir(".")

  git.showBranches()
  println(git.remoteOrigin)

  // val git = GitWrapper.fromRepoName("fand/evil")
  // git.showBranches()
}
