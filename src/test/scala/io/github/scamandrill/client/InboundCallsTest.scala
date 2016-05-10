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
    val res = Await.result(client.inboundAddDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }

  "InboundDomains" should "work getting a valid List[MInboundDomainResponse] (async client)" in {
    val res = Await.result(client.inboundDomains(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MInboundDomainResponse]
  }

  "InboundCheckDomain" should "work getting a valid MInboundDomainResponse (async client)" in {
    val res = Await.result(client.inboundCheckDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }

  "InboundDeleteDomains" should "work getting a valid MInboundDomainResponse (async client)" in {
    val res = Await.result(client.inboundDeleteDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }
}

