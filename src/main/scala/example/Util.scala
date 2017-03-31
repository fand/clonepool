package io.github.fand.clonepool
import scala.sys.process.Process
import scala.util.control.Exception._
import java.io.File
import java.io.ByteArrayInputStream

package object util {
  implicit class ThrowableOption[T](option: Option[T]) {
    def getOrThrow(e: Exception): T = option.getOrElse(throw e)
  }

  def exec(command: String, dir: String) =
    allCatch opt Process(command, new File(dir)).lineStream.toList

  def execT(command: String, dir: String, error: String) =
    exec(command, dir).getOrThrow(new Exception(error))

  def peco(list: List[String]): String = {
    val input = new ByteArrayInputStream(list.mkString("\n").getBytes("UTF-8"))
    ((Process("cat") #< input) #| Process("peco")).lineStream.toList(0)
  }
}
