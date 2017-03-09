package io.github.fand.clonepool
import java.io.File
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib._
import org.eclipse.jgit.api.errors._
import scala.collection.JavaConversions._

object Main extends App {

  val CLONES_PER_REPO = 4
  val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool/"

  type Repo = String
  type Uri = String

  val repo = "fand/md2hatena"
  var git = getGitInstance(repo)

  try {
    git.fetch().call();
  } catch {
    case e: GitAPIException => throw new RuntimeException(e)
    case _: Throwable => println("fetch failed")
  }

  // ブランチ名の一覧取得(Ref の getName()で「refs/remotes/origin/master」のように取得できる)
  val branchList = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call();

  for (b <- branchList.toList) {
    println(b)
  }

  def getGitInstance(repo: Repo): Git = {
    if (doesPoolExist(repo) && !shouldClone(repo)) {
      getGitExisting(repo)
    }
    else {
      getGitClone(repo)
    }
  }

  def doesPoolExist(repo: Repo): Boolean = {
    new File(getCloneDst(repo)).exists
  }

  def shouldClone(repo: Repo): Boolean = {
    new File(getCloneDst(repo)).list.size < CLONES_PER_REPO
  }

  def getOriginUri(repo: Repo): Uri = {
    s"https://github.com/$repo"
  }

  def getCloneDst(repo: Repo): String = {
    s"$CLONEPOOL_ROOT/$repo/"
  }

  def getGitClone(repo: Repo): Git = {
    new CloneCommand()
      .setDirectory(new File(getCloneDst(repo)))
      .setURI(getOriginUri(repo))
      .call()
  }

  def getGitExisting(repo: Repo): Git = {
    new Git(
      new RepositoryBuilder()
        .setWorkTree(new File(getCloneDst(repo)))
        .build()
    )
  }

}
