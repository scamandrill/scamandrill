package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._
import io.github.scamandrill.MandrillTestUtils._

import scala.util.Failure
import scala.util.Success

import org.scalatest.tagobjects.Retryable


class SubaccountCallsTest extends MandrillSpec {

  "SubaccountAdd" should "work getting a valid MSubaccountsResponse" taggedAs(Retryable) in {
    val res: MSubaccountsResponse = Await.result(
      client.subaccountAdd(validSubaccount), DefaultConfig.defaultTimeout
    )
    res.id shouldBe validSubaccount.id
    res.name shouldBe validSubaccount.name
  }

  "SubaccountPause" should "work getting a valid MSubaccountsResponse" in {
    val res = Await.result(client.subaccountPause(MSubaccountInfo(id = validSubaccount.id)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSubaccountsResponse]
    res.id shouldBe validSubaccount.id
    res.name shouldBe validSubaccount.name
    res.status shouldBe "paused"
  }

  "SubaccountResume" should "work getting a valid MSubaccountsResponse" in {
    val res = Await.result(client.subaccountResume(MSubaccountInfo(id = validSubaccount.id)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSubaccountsResponse]
    res.id shouldBe validSubaccount.id
    res.name shouldBe validSubaccount.name
    res.status shouldBe "active"
  }

  "SubaccountUpdate" should "work getting a valid MSubaccountsResponse" in {
    val res = Await.result(client.subaccountUpdate(validSubaccount.copy(notes = "updated")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSubaccountsResponse]
    res.id shouldBe validSubaccount.id
    res.name shouldBe validSubaccount.name
  }

  "SubaccountInfo" should "work getting a valid MSubaccountsResponse" in {
    val res = Await.result(client.subaccountInfo(MSubaccountInfo(id = validSubaccount.id)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSubaccountsInfoResponse]
  }

  "SubaccountList" should "work getting a valid List[MSubaccountsResponse]" in {
    val res = Await.result(client.subaccountList(MSubaccountList(q = "test")), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MSubaccountsResponse]
  }

  "SubaccountDelete" should "work getting a valid MSubaccountsResponse" in {
    val res = Await.result(client.subaccountDelete(MSubaccountInfo(id = validSubaccount.id)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSubaccountsResponse]
    res.id shouldBe validSubaccount.id
    res.name shouldBe validSubaccount.name
  }
}
