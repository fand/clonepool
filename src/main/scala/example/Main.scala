package io.github.fand.clonepool
import io.github.fand.clonepool.lib._
import scala.util.control.Exception._
import scala.sys.process.{Process, ProcessLogger}
import java.io.ByteArrayInputStream

object Main extends App {
  initialize()

  val l = List("foo", "bar", "baz")
  val s = new ByteArrayInputStream(l.mkString("\n").getBytes("UTF-8"))
  val o = ((Process("cat") #< s) #| Process("peco")).lines
  o.foreach(println)

  // val repo = Repo.fromDir(".")
  // // val repo = Repo.fromName("hatena/mackerel3")
  //
  // println(repo)

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
