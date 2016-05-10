package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._

import scala.util.{Failure, Try}
import io.github.scamandrill.MandrillTestUtils._

import scala.util.Success

class SendersCallsTest extends MandrillSpec {

  "SendersList" should "work getting a valid List[MSendersListResp] (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersList(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MSendersListResp]
    res.head.address shouldBe "scamandrill@test.com"
  }

  "SendersDomains" should "work getting a valid List[MSendersDomainResponses] (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersDomains(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MSendersDomainResponses]
    res.head.domain shouldBe "gmail.com"
  }

  "SendersAddDomain" should "work getting a valid MSendersDomainResponses (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersAddDomain(MSenderDomain(domain ="test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersDomainResponses]
    res.domain shouldBe "test.com"
  }

  "SendersCheckDomain" should "work getting a valid MSendersDomainResponses (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersCheckDomain(MSenderDomain(domain ="test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersDomainResponses]
    res.domain shouldBe "test.com"
  }

  "SendersVerifyDomain" should "work getting a valid MSendersVerifyDomResp (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersVerifyDomain(MSenderVerifyDomain(mailbox="joypeg.tech",domain ="gmail.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersVerifyDomResp]
    res.domain shouldBe "gmail.com"
  }

  "SendersInfo" should "work getting a valid MSendersInfoResp (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersInfo(MSenderAddress(address ="scamandrill@test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersInfoResp]
    res.address shouldBe "scamandrill@test.com"
  }

  "SendersTimeSeries" should "work getting a valid List[MSenderTSResponse] (async client)" in {
    val res: List[MSenderTSResponse] = Await.result(
      mandrillAsyncClient.sendersTimeSeries(MSenderAddress(address ="scamandrill@test.com")), DefaultConfig.defaultTimeout
    )
    res shouldBe Nil
  }
}
