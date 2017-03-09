package io.github.fand.clonepool
import java.io.File
import org.eclipse.jgit.api._
import org.eclipse.jgit.lib._

object Main extends App {

  /**
   * まずCloneなりローカルなレポジトリを教えるなりして生成したgitのインスタンスを叩くことで各種レポジトリ操作を実行する。
   * コマンドはメソッドチェインで構築し、call()で実行する。
   * やらかすと例外を吐いて無慈悲に死ぬ仕様になっているっぽい。
   */
  val originUri: String = "https://github.com/fand/md2hatena"

  val workingTree: File = new File(System.getProperty("user.home") + s"/.clonepool/md2hatena/")

  val git: Git = getGitInstance()

  def getGitInstance() = {
    val isInRepository = false
    if (isInRepository) {
      new Git(
        new RepositoryBuilder()
          .setWorkTree(workingTree)
          .build()
      )
    }
    else {
      new CloneCommand()
        .setDirectory(workingTree)
        .setURI(originUri)
        .call()
    }
  }

}
