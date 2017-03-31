package io.github.fand.clonepool.docs
import scalacss.Defaults._
import java.io.{File, PrintWriter}

object Main {
  def main(args: Array[String]) = {
    save("docs/styles.css", Styles.render)
  }

  def save(path: String, content: String) {
    val f = new File(path)
    val p = new java.io.PrintWriter(f)
    try {
      p.print(content)
    }
    finally {
      p.close()
    }
  }
}
