package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._
import io.github.scamandrill.MandrillTestUtils._

import scala.util.Failure
import scala.util.Success

class IspCallsTest  extends MandrillSpec {

  "IspList" should "work getting a valid List[MIspResponse] (async client)" in {
    val res = Await.result(mandrillAsyncClient.ispList(MKey()), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

  "IspCreatePool" should "work getting a valid MIspInfoPool (async client)" in {
    val res = Await.result(mandrillAsyncClient.ispCreatePool(MIspPoolInfo(pool = "test")), DefaultConfig.defaultTimeout)
    res.name shouldBe "test"
  }

  "IspListPool" should "work getting a valid List[MIspInfoPool] (async client)" in {
    val res = Await.result(mandrillAsyncClient.ispListPool(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MIspInfoPool]
  }

  "IspPoolInfo" should "work getting a valid MIspPoolInfo (async client)" in {
    val res = Await.result(mandrillAsyncClient.ispPoolInfo(MIspPoolInfo(pool = "test")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MIspInfoPool]
  }

  "IspDeletePool" should "work getting a valid MIspDeletePoolResponse (async client)" in {
    val res = Await.result(mandrillAsyncClient.ispDeletePool(MIspPoolInfo(pool = "test")), DefaultConfig.defaultTimeout)
    res.deleted shouldBe true
  }
}
