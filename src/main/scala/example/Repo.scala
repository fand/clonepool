package io.github.fand.clonepool.lib
import scala.reflect.ClassTag
import io.github.fand.clonepool.util._

object Repo {
  def isDirInRepo(dir: String) = exec("git rev-parse --show-toplevel", dir).isDefined

  def fromDir(dir: String): Repo = {
    val root = execT("git rev-parse --show-toplevel", dir, s"Invalid directory: $dir")(0)
    val origin = execT("git remote get-url origin", root, "Invalid remote origin found")(0)
    new Repo(root, origin)
  }

  def fromName(name: String): Repo = {
    var repoCandidates = exec("ghq list", ".").getOrElse(Nil)
      .filter(_.matches(s".*$name.*"))

    if (repoCandidates.size == 0) {
      exec(s"ghq get $name", ".") match {
        case None => throw new Exception("ghq get failed")
        case _ => println(s"ghq get $name succeeded")
      }
      repoCandidates = exec("ghq list", ".").getOrElse(Nil)
        .filter(_.matches(s".*$name.*"))
    }

    val repoPath = repoCandidates.size match {
      case 1 => repoCandidates(0)
      case _ => peco(repoCandidates)
    }
    val ghqRootOption = exec("ghq root", ".").getOrElse(Nil).headOption
    ghqRootOption match {
      case None => throw new Exception("ghq root is broken")
      case Some(ghqRoot) => fromDir(s"$ghqRoot/$repoPath")
    }
  }
}

case class Repo(root: String, origin: String) {
  private val sshPattern = "git@(.*)\\:(.*)/(.*)\\.git".r
  private val httpsPattern = "https://(.*)/(.*)/(.*)".r

  val (site, user, project) = origin match {
    case sshPattern(site, user, project) => (site, user, project)
    case httpsPattern(site, user, project) => (site, user, project)
    case _ => new Exception(s"Invalid origin: $origin")
  }

  def branches: List[String] =
    execT("git branch", root, s"git branch died. Is the repository broken?: $root")
      .map(_.replaceFirst("^\\* ", ""))
      .map(_.trim)

  def currentBranch: String =
    execT("git branch", root, s"git branch died. Is the repository broken?: $root")
      .filter(_.matches("^\\* .*"))
      .headOption
      .getOrThrow(new Exception("No main branch found"))
      .replaceFirst("^\\* ", "")

  def path = s"$site/$user/$project"
}
