package io.github.fand.clonepool.lib
import java.io.File
import scala.collection.JavaConversions._
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib._
import org.eclipse.jgit.api.errors._
import org.eclipse.jgit.transport.RemoteConfig

object GitWrapper {

  def fromDir(dir: String) = new GitWrapper(getGitForDir(dir))
  // def fromRepo(repo: Repo) = new GitWrapper(getGitForDir(dir))

  def getGitClone(repo: Repo) =
    new CloneCommand()
      .setDirectory(new File(repo.cloneDst))
      .setURI(repo.originUri)
      .call()

  def getGitExisting(repo: Repo) =
    new Git(
      new RepositoryBuilder()
        .setWorkTree(new File(repo.cloneDst))
        .build()
    )

  def getGitForDir(dir: String) = new Git(
    new RepositoryBuilder()
    .setWorkTree(new File(dir))
    .build()
  )

}

class GitWrapper(private val git: Git) {

  def fetch() = git.fetch().call()

  // TODO: Add option to set setListMode
  def branches = git.branchList().setListMode(ListBranchCommand.ListMode.ALL).call()

  def remoteList = git.remoteList().call()

  def remoteOrigin = {
    val remote = remoteList.filter(_.getName().matches("origin")) match {
      case l if (l.size == 0) => throw new Exception("Repositories must have remote origin")
      case l if (l.size > 1) => throw new Exception(s"Invalid repository. There are too many remote/origin")
      case x => x(0)
    }

    val uri = remote.getURIs() match {
      case l if (l.size == 0) => throw new Exception("Remote origin must have URI")
      case l if (l.size > 1) => throw new Exception(s"Invalid repository. Multiple URIs for remote/origin")
      case l => l(0).toString()
    }

    splitRemoteURI(uri)
  }

  private def splitRemoteURI(uri: String) = {
    println(uri)
    val re = "^(?:git@)?(.*):(.*)\\.git$".r
    uri match {
      case re(site, reponame) => (site, reponame)
      case _ => throw new Exception("failed to parse remote URI")
    }
  }

  def showBranches() = {
    for (b <- branches) {
      println(b.getName().replaceAll("refs/(heads|remotes)/", ""))
    }
  }

}
