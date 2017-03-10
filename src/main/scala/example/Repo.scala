package io.github.fand.clonepool.lib
import java.io.File
import scala.collection.JavaConversions._

class Repo(private val repo: String) {
  val CLONES_PER_REPO = 4
  val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool/"

  type Repo = String
  type Uri = String

  def shouldClone: Boolean =
    !doesPoolExist || canCloneMore

  def doesPoolExist: Boolean = {
    new File(cloneDst).exists
  }

  def canCloneMore: Boolean = {
    new File(cloneDst).list.size < CLONES_PER_REPO
  }

  def originUri: Uri = {
    s"git@github.com:$repo.git"
  }

  def cloneDst: String = {
    s"$CLONEPOOL_ROOT/$repo/"
  }
}
