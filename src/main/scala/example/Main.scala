package io.github.fand.clonepool
import java.io.File
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib._
import org.eclipse.jgit.api.errors._
import scala.collection.JavaConversions._
import scala.util.control.Exception._

object Main extends App {

  val CLONES_PER_REPO = 4
  val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool/"

  type Repo = String
  type Uri = String

  showBranches("fand/evil")

  def showBranches(repo: Repo) = {
    var git = getGitInstance(repo)

    // ブランチ名の一覧取得(Ref の getName()で「refs/remotes/origin/master」のように取得できる)
    for {
      branches <- getBranches(git)
      b <- branches
    } {
      val s = b.getName().replaceAll("refs/(heads|remotes)/", "")
      println(s)
    }
  }

  def fetch(git: Option[Git]) = git.flatMap(g =>
     allCatch opt g.fetch().call()
  )

  def getBranches(git: Option[Git]) = git.flatMap(g =>
    allCatch opt {
      g.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()
      // g.branchList().call()
    }
  )

  def getGitInstance(repo: Repo): Option[Git] = {
    if (shouldClone(repo)) {
      getGitClone(repo)
    }
    else {
      getGitExisting(repo)
    }
  }

  def shouldClone(repo: Repo): Boolean =
    !doesPoolExist(repo) || canCloneMore(repo)

  def doesPoolExist(repo: Repo): Boolean = {
    new File(getCloneDst(repo)).exists
  }

  def canCloneMore(repo: Repo): Boolean = {
    new File(getCloneDst(repo)).list.size < CLONES_PER_REPO
  }

  def getOriginUri(repo: Repo): Uri = {
    s"https://github.com/$repo"
  }

  def getCloneDst(repo: Repo): String = {
    s"$CLONEPOOL_ROOT/$repo/"
  }

  def getGitClone(repo: Repo): Option[Git] = allCatch opt {
    new CloneCommand()
      .setDirectory(new File(getCloneDst(repo)))
      .setURI(getOriginUri(repo))
      .call()
  }

  def getGitExisting(repo: Repo): Option[Git] = allCatch opt {
    new Git(
      new RepositoryBuilder()
        .setWorkTree(new File(getCloneDst(repo)))
        .build()
    )
  }

}
