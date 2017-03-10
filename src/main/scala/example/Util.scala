package io.github.fand.clonepool
package object util {
  implicit class ThrowableOption[T](option: Option[T]) {
    def getOrThrow(e: Exception): T = option.getOrElse(throw e)
  }
}
