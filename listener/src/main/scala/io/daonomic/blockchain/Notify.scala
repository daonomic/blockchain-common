package io.daonomic.blockchain

import cats.Monad
import cats.implicits._

import scala.language.higherKinds

object Notify {
  def every[T, F[_]](list: Seq[T])(f: T => F[Unit])(implicit m: Monad[F]): F[Unit] =
    if (list.isEmpty) {
      m.unit
    } else {
      val head = list.head
      val tail = list.tail
      f(head).flatMap(_ => every(tail)(f))
    }
}
