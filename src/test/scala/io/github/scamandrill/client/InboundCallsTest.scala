package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.concurrent.Await

class InboundCallsTest extends MandrillSpec {

  "InboundAddDomains" should "work getting a valid MInboundDomainResponse" in {
    val res = Await.result(client.inboundAddDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }

  "InboundDomains" should "work getting a valid List[MInboundDomainResponse]" in {
    val res = Await.result(client.inboundDomains(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MInboundDomainResponse]
  }

  "InboundCheckDomain" should "work getting a valid MInboundDomainResponse" in {
    val res = Await.result(client.inboundCheckDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }

  "InboundDeleteDomains" should "work getting a valid MInboundDomainResponse" in {
    val res = Await.result(client.inboundDeleteDomain(MInboundDomain(domain = "testingdomain")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "testingdomain"
    res.valid_mx shouldBe false
  }
}

