package io.daonomic.blockchain.common

import java.math.BigInteger

import cats.Monad
import cats.implicits._
import io.daonomic.blockchain.Notify
import io.daonomic.blockchain.state.State

import scala.language.higherKinds

abstract class AbstractListenService[F[_]](confidence: Int, state: State[BigInteger, F])
                                          (implicit m: Monad[F]) {
  def check(blockNumber: BigInteger): F[Unit] = for {
    saved <- state.get
    _ <- fetchAndNotify(blockNumber, saved)
  } yield ()

  private def fetchAndNotify(blockNumber: BigInteger, saved: Option[BigInteger]): F[Unit] = {
    val from = saved.getOrElse(blockNumber.subtract(BigInteger.ONE))
    val start = from.subtract(BigInteger.valueOf(confidence - 1))
    val numbers = blockNumbers(start, blockNumber)
    Notify.every(numbers) { num =>
      fetchAndNotify(blockNumber)(num).flatMap(_ => state.set(num))
    }
  }

  private def blockNumbers(from: BigInteger, to: BigInteger): TraversableOnce[BigInteger] = {
    if (from.compareTo(to) >= 0)
      Nil
    else if (from.compareTo(BigInteger.ZERO) < 0)
      (BigInt(BigInteger.ZERO) to to).map(_.bigInteger)
    else
      (BigInt(from) to to).map(_.bigInteger)
  }

  protected def fetchAndNotify(latestBlock: BigInteger)(block: BigInteger): F[Unit]
}
