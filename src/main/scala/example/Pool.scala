package io.github.fand.clonepool.lib
import io.github.fand.clonepool.util._
import java.io.File

case class Pool(repo: Repo) {
  private val CLONES_PER_REPO = 4
  private val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool"

  val cloneDst = s"$CLONEPOOL_ROOT/${repo.site}/${repo.user}/${repo.project}"

  def shouldClone: Boolean =
    !doesPoolExist || canCloneMore

  def doesPoolExist: Boolean = {
    new File(cloneDst).exists
  }

  def canCloneMore: Boolean = {
    new File(cloneDst).list.size < CLONES_PER_REPO
  }

  def nextIndex: Int =
    new File(cloneDst).list.size + 1
}
