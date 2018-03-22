package io.daonomic.blockchain.poller.mono

import io.daonomic.blockchain.poller.Poller
import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono

object implicits {
  implicit val monoSleeper: MonoSleeper = new MonoSleeper
  implicit val monoPoller: Poller[Mono] = new Poller[Mono](monoSleeper)
}
