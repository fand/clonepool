package io.github.fand.clonepool.lib
import io.github.fand.clonepool.util._
import java.io.File

object Pool {
  private val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool"

  def clones = {
    val sites = new File(CLONEPOOL_ROOT).list.toList
    val users = sites.flatMap(s => new File(s"$CLONEPOOL_ROOT/$s").list.toList.map(u => s"$s/$u"))
    val pools = users.flatMap(u => new File(s"$CLONEPOOL_ROOT/$u").list.toList.map(p => s"$u/$p"))
    pools.flatMap(p => Pool(p).clones.map(c => s"$p/$c"))
  }
}

case class Pool(repopath: String) {
  private val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool"

  val path = s"$CLONEPOOL_ROOT/$repopath"

  def doesPoolExist: Boolean =
    new File(path).exists

  def hasClone(branch: String): Boolean =
    new File(s"$path/$branch").exists

  def clones: List[String] =
    new File(path).list.toList

  def createPool(): Unit =
    new File(path).mkdirs()

  def createClone(src: String, branch: String): Unit = {
    exec(s"cp -r ${src} $path/$branch", ".")
    val repo = Repo.fromDir(s"$path/$branch")

    exec(s"git reset --hard", s"$path/$branch")
    exec(s"git checkout .", s"$path/$branch")

    if (repo.branches.contains(branch)) {
      exec(s"git checkout $branch", s"$path/$branch")
    }
    else {
      exec(s"git checkout -b $branch", s"$path/$branch")
    }
  }

}
