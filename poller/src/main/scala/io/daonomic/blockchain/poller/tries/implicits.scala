package io.daonomic.blockchain.poller.tries

import cats.implicits._
import io.daonomic.blockchain.poller.Poller

import scala.util.Try

object implicits {
  implicit val trySleeper: TrySleeper = new TrySleeper
  implicit val tryPoller: Poller[Try] = new Poller[Try](trySleeper)
}
