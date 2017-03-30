package io.github.fand.clonepool.lib
import io.github.fand.clonepool.util._
import scala.sys.process.Process
import scala.util.control.Exception._
import java.io.File

case class Pool(repo: Repo) {
  private def exec(command: String, dir: String) = allCatch opt Process(command, new File(dir)).lineStream.toList

  private val CLONES_PER_REPO = 4
  private val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool"

  val cloneDst = s"$CLONEPOOL_ROOT/${repo.site}/${repo.user}/${repo.project}"

  def shouldClone: Boolean =
    !doesPoolExist || canCloneMore

  def doesPoolExist: Boolean = {
    new File(cloneDst).exists
  }

  def hasClone(branch: String): Boolean = {
    new File(s"$cloneDst/$branch").exists
  }

  def canCloneMore: Boolean = {
    new File(cloneDst).list.size < CLONES_PER_REPO
  }

  def nextIndex: Int =
    new File(cloneDst).list.size + 1

  // def clones: List[String] = new File(cloneDst).list.toList
  def clones: List[String] = {
    println(cloneDst)
    new File(cloneDst).list.toList
  }

  def createPool(): Unit = {
    new File(cloneDst).mkdirs()
  }

  def createClone(branch: String): Repo = {
    val newRoot = s"$cloneDst/$branch"
    exec(s"cp -r ${repo.root} $newRoot", ".")
    repo.copy(root = newRoot)
  }

}
