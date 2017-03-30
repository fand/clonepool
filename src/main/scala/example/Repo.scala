package io.github.fand.clonepool.lib
import scala.reflect.ClassTag
import scala.sys.process.Process
import scala.util.control.Exception._
import java.io.File
import java.io.ByteArrayInputStream
import io.github.fand.clonepool.util._

object Repo {
  private def exec(command: String, dir: String) = allCatch opt Process(command, new File(dir)).lineStream.toList

  def fromDir(dir: String): Repo = {
    val root: String = exec("git rev-parse --show-toplevel", dir) match {
      case Some(x :: Nil) => x
      case _ => throw new Exception(s"Invalid directory: $dir")
    }

    val origin: String = exec("git remote get-url origin", root) match {
      case Some(x :: Nil) => x
      case _ => throw new Exception("Invalid remote origin found")
    }

    new Repo(root, origin)
  }

  def fromName(name: String): Repo = {
    val repoCandidates = exec("ghq list", ".").getOrElse(Nil)
      .filter(_.matches(s".*$name.*"))

    if (repoCandidates.size > 0) {
      val repoPath = peco(repoCandidates)
      val ghqRootOption = exec("ghq root", ".").getOrElse(Nil).headOption
      ghqRootOption match {
        case None => throw new Exception("ghq root is broken")
        case Some(ghqRoot) => fromDir(s"$ghqRoot/$repoPath")
      }
    }
    else {
      exec(s"ghq get $name", ".") match {
        case None => throw new Exception("ghq get failed")
        case _ => println(s"ghq get $name succeeded")
      }
      val ghqRootOption = exec("ghq root", ".").getOrElse(Nil).headOption
      ghqRootOption match {
        case None => throw new Exception("ghq root is broken")
        case Some(ghqRoot) => fromDir(s"$ghqRoot/$name")
      }
    }
  }

  def peco(list: List[String]): String = {
    val input = new ByteArrayInputStream(list.mkString("\n").getBytes("UTF-8"))
    ((Process("cat") #< input) #| Process("peco")).lineStream.toList(0)
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

  private def exec(command: String, error: String) =
    Repo.exec(command, root).getOrThrow(new Exception(error))

  def branches: List[String] =
    exec("git branch", s"git branch died. Is the repository broken?: $root")
      .map(_.replaceFirst("^\\* ", ""))
      .map(_.trim)

  def currentBranch: String =
    exec("git branch", s"git branch died. Is the repository broken?: $root")
      .filter(_.matches("^\\* .*"))
      .headOption
      .getOrThrow(new Exception("No main branch found"))
      .replaceFirst("^\\* ", "")

}
