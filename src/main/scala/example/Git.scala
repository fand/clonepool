// package io.github.fand.clonepool.lib
// import java.io.File
// import scala.collection.JavaConversions._
// import scala.sys.process.Process
//
// object Git {
//
//   def fromDir(dir: String) = new Git(getGitForDir(dir))
//
//   def fromRepoName(name: String) = {
//     new Git(new Repo(name))
//   }
//
//   private def getGitForRepo(repo: Repo) = {
//     if (repo.shouldClone) {
//       getGitClone(repo)
//     }
//     else {
//       getGitExisting(repo)
//     }
//   }
//
//   private def getGitClone(repo: Repo) =
//     Process(s"git clone ${repo.originUri} ${repo.cloneDst}").run
//
//   // private def getGitExisting(repo: Repo) =
//   //   new Git(
//   //     new RepositoryBuilder()
//   //       .setWorkTree(new File(repo.cloneDst))
//   //       .build()
//   //   )
//   //
//   // private def getGitForDir(dir: String) = new Git(
//   //   new RepositoryBuilder()
//   //   .setWorkTree(new File(dir))
//   //   .build()
//   // )
//
// }
//
// class Git(private val repo: Repo) {
//
//   //
//
//   def fetch() = Process("git fetch").run
//
//   // TODO: Add option to set setListMode
//   def branches = Process("git branch").lines
//
//   def remoteList = git.remoteList().call()
//
//   def remoteOrigin = {
//     val remote = remoteList.filter(_.getName().matches("origin")) match {
//       case l if (l.size == 0) => throw new Exception("Repositories must have remote origin")
//       case l if (l.size > 1) => throw new Exception(s"Invalid repository. There are too many remote/origin")
//       case x => x(0)
//     }
//
//     val uri = remote.getURIs() match {
//       case l if (l.size == 0) => throw new Exception("Remote origin must have URI")
//       case l if (l.size > 1) => throw new Exception(s"Invalid repository. Multiple URIs for remote/origin")
//       case l => l(0).toString()
//     }
//
//     splitRemoteURI(uri)
//   }
//
//   private def splitRemoteURI(uri: String) = {
//     println(uri)
//     val ssh = "^(?:git@)?(.*):(.*)\\.git$".r
//     val https = "^https\\://(.*?)/(.*)$".r
//     uri match {
//       case ssh(site, reponame) => (site, reponame)
//       case https(site, reponame) => (site, reponame)
//       case _ => throw new Exception("failed to parse remote URI")
//     }
//   }
//
//   def showBranches() = {
//     for (b <- branches) {
//       println(b.getName().replaceAll("refs/(heads|remotes)/", ""))
//     }
//   }
//
// }
