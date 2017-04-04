package io.github.fand.clonepool.docs

object Util {
  def dedent(str: String) = {
    val lines = str.split("\n").tail.init
    val indent = "^(\\s*).*$".r
    val level = lines.map(line => line match {
      case indent(s) => s.length
      case _ => 0
    }).min
    lines.map(_.substring(level)).mkString("\n")
  }
}
