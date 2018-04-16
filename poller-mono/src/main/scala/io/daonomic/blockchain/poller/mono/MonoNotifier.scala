package io.daonomic.blockchain.poller.mono

import io.daonomic.blockchain.poller.Notifier
import reactor.core.publisher.Mono

import scala.collection.JavaConverters._

class MonoNotifier extends Notifier[Mono] {
  override def notify[T](list: Seq[T])(f: T => Mono[Unit]): Mono[Unit] =
    MonoChain.consequently[T](list.asJava, value => f(value).then()).then(Mono.just())
}
