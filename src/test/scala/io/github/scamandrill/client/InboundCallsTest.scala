package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._
import io.github.scamandrill.MandrillTestUtils._

import scala.util.Failure
import scala.util.Success

import org.scalatest.tagobjects.Retryable

class InboundCallsTest extends MandrillSpec {

  "InboundAddDomains" should "work getting a valid MInboundDomainResponse (async client)" in {
    val res = Await.result(mandrillAsyncClient.inboundAddDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }

  "InboundDomains" should "work getting a valid List[MInboundDomainResponse] (async client)" in {
    val res = Await.result(mandrillAsyncClient.inboundDomains(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MInboundDomainResponse]
  }

  "InboundCheckDomain" should "work getting a valid MInboundDomainResponse (async client)" in {
    val res = Await.result(mandrillAsyncClient.inboundCheckDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }

  "InboundDeleteDomains" should "work getting a valid MInboundDomainResponse (async client)" in {
    val res = Await.result(mandrillAsyncClient.inboundDeleteDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }
}

