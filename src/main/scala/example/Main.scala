package io.github.fand.clonepool
import io.github.fand.clonepool.lib._
import scala.util.control.Exception._

object Main extends App {
  // val jschConfigSessionFactory = new CustomConfigSessionFactory;
  // SshSessionFactory.setInstance(jschConfigSessionFactory);

  // val git = GitWrapper.fromDir(".")
  val git = GitWrapper.fromRepoName("hatena/mackerel3")

  git.showBranches()
  println(git.remoteOrigin)
}
