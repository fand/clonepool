package io.github.fand.clonepool.lib
import scala.util.control.Exception._
import scala.sys.process.Process
import java.io.File

import scala.sys.process.{Process, ProcessLogger}
import java.io.ByteArrayInputStream

object Repo {
  private def exec(command: String, dir: String) = allCatch opt Process(command, new File(dir)).lineStream.toList

  def fromDir(dir: String): Repo = {
    // 親ディレクトリを遡って .git ディレクトリのあるディレクトリを探す
    // .git/config からoriginを取得する
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

  def branches: List[String] =
    Repo.exec("git branch", root).getOrElse(Nil)
      .map(_.replaceFirst("^\\* ", ""))
      .map(_.trim)

  // val CLONES_PER_REPO = 4
  // val CLONEPOOL_ROOT = System.getProperty("user.home") + s"/.clonepool/"
  //
  // type Repo = String
  // type Uri = String
  //
  // def shouldClone: Boolean =
  //   !doesPoolExist || canCloneMore
  //
  // def doesPoolExist: Boolean = {
  //   new File(cloneDst).exists
  // }
  //
  // def canCloneMore: Boolean = {
  //   new File(cloneDst).list.size < CLONES_PER_REPO
  // }
  //
  // def originUri: Uri = {
  //   s"git@github.com:$repo.git"
  // }
  //
  // def poolPath: String =
  //   s"$CLONEPOOL_ROOT/$site/$repo/"
  //
  // def clonePath: String =
  //   s"$poolPath/$nextIndex"
  //
  // def nextIndex: Int =
  //   new File(cloneDst).list.size + 1

}
