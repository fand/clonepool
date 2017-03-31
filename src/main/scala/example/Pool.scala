package io.github.fand.clonepool.lib
import io.github.fand.clonepool.util._
import scala.sys.process.Process
import scala.util.control.Exception._
import java.io.File

object Pool {
  private val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool"

  def clones = {
    val sites = new File(CLONEPOOL_ROOT).list.toList.map(s => s"$CLONEPOOL_ROOT/$s") // .clonepool/github.com
    val users = sites.flatMap(s => new File(s).list.toList.map(u => s"$s/$u")) // .clonepool/github.com/fand
    val pools = users.flatMap(u => new File(u).list.toList.map(p => s"$u/$p"))
    pools.flatMap(p => Pool(p).clones.map(c => s"$p/$c"))
  }
}

case class Pool(repopath: String) {
  private def exec(command: String, dir: String) = allCatch opt Process(command, new File(dir)).lineStream.toList

  private val CLONES_PER_REPO = 4
  private val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool"

  val path = s"$CLONEPOOL_ROOT/$repopath"

  def shouldClone: Boolean =
    !doesPoolExist || canCloneMore

  def doesPoolExist: Boolean =
    new File(path).exists

  def hasClone(branch: String): Boolean =
    new File(s"$path/$branch").exists

  def canCloneMore: Boolean =
    new File(path).list.size < CLONES_PER_REPO

  def nextIndex: Int =
    new File(path).list.size + 1

  def clones: List[String] = {
    new File(path).list.toList
  }

  def createPool(): Unit =
    new File(path).mkdirs()

  def createClone(src: String, branch: String): Unit = {
    exec(s"cp -r ${src} $path/$branch", ".")
  }

}
