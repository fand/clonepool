package io.github.fand.clonepool
import java.io.File
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib._
import org.eclipse.jgit.api.errors._
import org.eclipse.jgit.transport.RemoteConfig
import scala.collection.JavaConversions._

import scala.util.control.Exception._

object Main extends App {

  val CLONES_PER_REPO = 4
  val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool/"

  type Repo = String
  type Uri = String

  val git = allCatch opt new Git(
    new RepositoryBuilder()
    .setWorkTree(new File("."))
    .build()
  )
  showBranches(git)
  println(">>>>>>>>>>>")

  val p = for {
    remoteList <- getRemoteList(git)
    remote <- remoteList
    uri <- remote.getURIs()
    parsed <- splitRemoteURI(uri.toString())
  } {
    println(parsed)
  }

  // getRemoteOrigin(git)

  def getRemoteList(git: Option[Git]): Option[Seq[RemoteConfig]] = git.flatMap(g =>
    allCatch opt g.remoteList().call()
  )

  // def getRemoteOrigin(git: Option[Git]) = {
  //   var remoteList = for {
  //     remoteList <- getRemoteList(git)
  //     remote <- remoteList
  //   } yield remote
  //   println(remoteList)
  // }

  // var git = getGitInstance("fand/evil")
  // showBranches(git)

  def showBranches(git: Option[Git]) = {
    for {
      branches <- getBranches(git)
      b <- branches
    } {
      val s = b.getName().replaceAll("refs/(heads|remotes)/", "")
      println(s)
    }
  }

  def splitRemoteURI(uri: Uri) = allCatch opt {
    val re = "^(?:git@)?(.*):(.*)\\.git$".r
    uri match {
      case re(site, reponame) => (site, reponame)
      case _ => throw new Exception("failed to parse remote URI")
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
