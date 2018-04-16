package io.daonomic.blockchain

import cats.Monad
import cats.implicits._

import scala.language.higherKinds

object Notify {
  def every[T, F[_]](list: TraversableOnce[T])(f: T => F[Unit])(implicit m: Monad[F]): F[Unit] = {
    list.foldLeft(m.unit)((monad, next) => monad.flatMap(_ => f(next)))
  }
}
