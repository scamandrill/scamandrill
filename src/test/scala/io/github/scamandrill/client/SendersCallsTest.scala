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
  it should "work getting a valid List[MSendersListResp] (blocking client)" in {
    mandrillBlockingClient.sendersList(MKey()) match {
      case Success(res) =>
        res.head.getClass shouldBe classOf[MSendersListResp]
        res.head.address shouldBe "scamandrill@test.com"
      case Failure(ex) => fail(ex)
    }
  }
  it should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.sendersList(MKey(key="invalid")))
  }

  "SendersDomains" should "work getting a valid List[MSendersDomainResponses] (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersDomains(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MSendersDomainResponses]
    res.head.domain shouldBe "gmail.com"
  }
  it should "work getting a validList[MSendersDomainResponses] (blocking client)" in {
    mandrillBlockingClient.sendersDomains(MKey()) match {
      case Success(res) =>
        res.head.getClass shouldBe classOf[MSendersDomainResponses]
        res.head.domain shouldBe "gmail.com"
      case Failure(ex) => fail(ex)
    }
  }
  it should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.sendersDomains(MKey(key="invalid")))
  }

  "SendersAddDomain" should "work getting a valid MSendersDomainResponses (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersAddDomain(MSenderDomain(domain ="test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersDomainResponses]
    res.domain shouldBe "test.com"
  }
  it should "work getting a valid MSendersDomainResponses (blocking client)" in {
    mandrillBlockingClient.sendersAddDomain(MSenderDomain(domain ="test.com")) match {
      case Success(res) =>
        res.getClass shouldBe classOf[MSendersDomainResponses]
        res.domain shouldBe "test.com"
      case Failure(ex) => fail(ex)
    }
  }
  it should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.sendersAddDomain(MSenderDomain(domain ="test.com",key="invalid")))
  }

  "SendersCheckDomain" should "work getting a valid MSendersDomainResponses (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersCheckDomain(MSenderDomain(domain ="test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersDomainResponses]
    res.domain shouldBe "test.com"
  }
  it should "work getting a valid MSendersDomainResponses (blocking client)" in {
    mandrillBlockingClient.sendersCheckDomain(MSenderDomain(domain ="test.com")) match {
      case Success(res) =>
        res.getClass shouldBe classOf[MSendersDomainResponses]
        res.domain shouldBe "test.com"
      case Failure(ex) => fail(ex)
    }
  }
  it should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.sendersCheckDomain(MSenderDomain(domain ="test.com",key="invalid")))
  }

  "SendersVerifyDomain" should "work getting a valid MSendersVerifyDomResp (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersVerifyDomain(MSenderVerifyDomain(mailbox="joypeg.tech",domain ="gmail.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersVerifyDomResp]
    res.domain shouldBe "gmail.com"
  }
  it should "work getting a valid MSendersDomainResponses (blocking client)" in {
    mandrillBlockingClient.sendersVerifyDomain(MSenderVerifyDomain(mailbox="scamandrill",domain ="gmail.com")) match {
      case Success(res) =>
        res.getClass shouldBe classOf[MSendersVerifyDomResp]
        res.domain shouldBe "gmail.com"
      case Failure(ex) => fail(ex)
    }
  }
  it should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.sendersVerifyDomain(MSenderVerifyDomain(mailbox="scamandrill",domain ="test.com",key="invalid")))
  }

  "SendersInfo" should "work getting a valid MSendersInfoResp (async client)" in {
    val res = Await.result(mandrillAsyncClient.sendersInfo(MSenderAddress(address ="scamandrill@test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MSendersInfoResp]
    res.address shouldBe "scamandrill@test.com"
  }
  it should "work getting a valid MSendersInfoResp (blocking client)" in {
    mandrillBlockingClient.sendersInfo(MSenderAddress(address ="scamandrill@test.com")) match {
      case Success(res) =>
        res.getClass shouldBe classOf[MSendersInfoResp]
        res.address shouldBe "scamandrill@test.com"
      case Failure(ex) => fail(ex)
    }
  }
  it should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.sendersInfo(MSenderAddress(address ="scamandrill@test.com",key="invalid")))
  }

  "SendersTimeSeries" should "work getting a valid List[MSenderTSResponse] (async client)" in {
    val res: List[MSenderTSResponse] = Await.result(
      mandrillAsyncClient.sendersTimeSeries(MSenderAddress(address ="scamandrill@test.com")), DefaultConfig.defaultTimeout
    )
    res shouldBe Nil
  }
  it should "work getting a valid List[MSenderTSResponse] (blocking client)" in {
    val res: Try[List[MSenderTSResponse]] = mandrillBlockingClient.sendersTimeSeries(
      MSenderAddress(address ="scamandrill@test.com")
    )
    res match {
      case Success(resSender) =>
        resSender shouldBe Nil
      case Failure(ex) => fail(ex)
    }
  }
  it should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.sendersTimeSeries(MSenderAddress(address ="scamandrill@gmail.com",key="invalid")))
  }

}
