package io.daonomic.blockchain.block

import java.math.BigInteger

import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.state.{MonoState, MonoStateAdapter}
import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono

class MonoBlockListenService(blockchain: Blockchain[Mono], listener: MonoBlockListener, state: MonoState[BigInteger]) {
  private val scala = new BlockListenService[Mono](blockchain, new MonoBlockListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(): Mono[BigInteger] =
    scala.check()
}
