package io.daonomic.blockchain.transfer
import java.math.BigInteger

import io.daonomic.blockchain.Blockchain
import io.daonomic.blockchain.state.{MonoState, MonoStateAdapter}
import io.daonomic.cats.mono.implicits._
import reactor.core.publisher.Mono

class MonoTransferListenService(blockchain: Blockchain[Mono], confidence: Int, listener: MonoTransferListener, state: MonoState[BigInteger]) {
  private val scala = new TransferListenService[Mono](blockchain, confidence, new MonoTransferListenerAdapter(listener), new MonoStateAdapter[BigInteger](state))

  def check(block: BigInteger): Mono[Void] =
    scala.check(block).`then`()
}
