package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.concurrent.Await

class IspCallsTest  extends MandrillSpec {

  "IspList" should "work getting a valid List[MIspResponse]" in {
    val res = Await.result(client.ispList(MKey()), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

  "IspCreatePool" should "work getting a valid MIspInfoPool" in {
    val res = Await.result(client.ispCreatePool(MIspPoolInfo(pool = "test")), DefaultConfig.defaultTimeout)
    res.name shouldBe "test"
  }

  "IspListPool" should "work getting a valid List[MIspInfoPool]" in {
    val res = Await.result(client.ispListPool(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MIspInfoPool]
  }

  "IspPoolInfo" should "work getting a valid MIspPoolInfo" in {
    val res = Await.result(client.ispPoolInfo(MIspPoolInfo(pool = "test")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MIspInfoPool]
  }

  "IspDeletePool" should "work getting a valid MIspDeletePoolResponse" in {
    val res = Await.result(client.ispDeletePool(MIspPoolInfo(pool = "test")), DefaultConfig.defaultTimeout)
    res.deleted shouldBe true
  }
}
