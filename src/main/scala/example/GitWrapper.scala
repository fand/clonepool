package io.github.fand.clonepool.lib
import java.io.File
import scala.collection.JavaConversions._
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib._
import org.eclipse.jgit.api.errors._
import org.eclipse.jgit.transport.RemoteConfig

object GitWrapper {

  import org.eclipse.jgit.api.TransportConfigCallback
  import org.eclipse.jgit.transport._

  val cb = new TransportConfigCallback {
    override def configure(transport: Transport): Unit = {
      val sshSessionFactory = new CustomConfigSessionFactory;
      val sshTransport = transport.asInstanceOf[SshTransport];
      sshTransport.setSshSessionFactory(sshSessionFactory);
    }
  }

  def fromDir(dir: String) = new GitWrapper(getGitForDir(dir))
  def fromRepoName(name: String) = {
    val repo = new Repo(name)
    new GitWrapper(getGitForRepo(repo))
  }

  private def getGitForRepo(repo: Repo) = {
    if (repo.shouldClone) {
      getGitClone(repo)
    }
    else {
      getGitExisting(repo)
    }
  }

  private def getGitClone(repo: Repo) =
    new CloneCommand()
      .setDirectory(new File(repo.cloneDst))
      .setURI(repo.originUri)
      .setTransportConfigCallback(cb)
      .call()

  private def getGitExisting(repo: Repo) =
    new Git(
      new RepositoryBuilder()
        .setWorkTree(new File(repo.cloneDst))
        .build()
    )

  private def getGitForDir(dir: String) = new Git(
    new RepositoryBuilder()
    .setWorkTree(new File(dir))
    .build()
  )

}

import org.eclipse.jgit.transport.JschConfigSessionFactory
import org.eclipse.jgit.transport.SshSessionFactory
import org.eclipse.jgit.util.FS
import com.jcraft.jsch.JSch
import com.jcraft.jsch.Session
import org.eclipse.jgit.transport.OpenSshConfig.Host

class CustomConfigSessionFactory extends JschConfigSessionFactory {
  override protected def getJSch(hc: Host, fs: FS): JSch = {
    val jsch = super.getJSch(hc, fs)
    jsch.removeAllIdentity()
    jsch.addIdentity("/Users/amagitakayosi/.ssh/id_rsa")
    jsch
  }

  override protected def configure(host: Host, session: Session): Unit = {
    // do nothing
  }

  // override protected def createDefaultJSch(fs: FS): JSch = {
  //   val jsch = super.createDefaultJSch(fs)
  //   jsch.addIdentity("/Users/amagitakayosi/.ssh/id_rsa")
  //   jsch
  // }
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
    val ssh = "^(?:git@)?(.*):(.*)\\.git$".r
    val https = "^https\\://(.*?)/(.*)$".r
    uri match {
      case ssh(site, reponame) => (site, reponame)
      case https(site, reponame) => (site, reponame)
      case _ => throw new Exception("failed to parse remote URI")
    }
  }

  def showBranches() = {
    for (b <- branches) {
      println(b.getName().replaceAll("refs/(heads|remotes)/", ""))
    }
  }

}
