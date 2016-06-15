package io.github.scamandrill.client

import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.models._

import scala.util.{Failure, Success}

class InboundCallsTest extends MandrillSpec {

  "InboundAddDomains" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=add-domain" in {
    withMockClient("/inbound/add-domain.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundAddDomain(MInboundDomain(
        domain = "inbound.example.com"
      )), defaultTimeout)(_ shouldBe Success(
        MInboundDomainResponse(
          domain = "inbound.example.com",
          created_at = "2013-01-01 15:30:27",
          valid_mx = true
        )
      ))
    }
  }

  it should "work getting a valid MInboundDomainResponse" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.inboundAddDomain(MInboundDomain(domain = "testingdomain")), defaultTimeout) {
        case Success(res) =>
          res.domain shouldBe "testingdomain"
          res.valid_mx shouldBe false
        case Failure(t) => fail(t)
      }
    }
  }

  "InboundCheckDomain" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=check-domain" in {
    withMockClient("/inbound/check-domain.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundCheckDomain(MInboundDomain(
        domain = "inbound.example.com"
      )), defaultTimeout)(_ shouldBe Success(
        MInboundDomainResponse(
          domain = "inbound.example.com",
          created_at = "2013-01-01 15:30:27",
          valid_mx = true
        )
      ))
    }
  }

  it should "work getting a valid MInboundDomainResponse" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.inboundCheckDomain(MInboundDomain(domain = "testingdomain")), defaultTimeout) {
        case Success(res) =>
          res.domain shouldBe "testingdomain"
          res.valid_mx shouldBe false
        case Failure(t) => fail(t)
      }
    }
  }

  "InboundDomains" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=domain" in {
    withMockClient("/inbound/domains.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundDomains(), defaultTimeout)(_ shouldBe Success(List(
        MInboundDomainResponse(
          domain = "inbound.example.com",
          created_at = "2013-01-01 15:30:27",
          valid_mx = true
        )
      )))
    }
  }

  it should "work getting a valid List[MInboundDomainResponse]" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.inboundDomains(), defaultTimeout) {
        case Success(res) => res.headOption match {
          case Some(dr) => dr shouldBe a[MInboundDomainResponse]
          case None => fail("Expected to get a non-empty list of domain responses")
        }
        case Failure(t) => fail(t)
      }
    }
  }

  "InboundDeleteDomains" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=delete-domain" in {
    withMockClient("/inbound/delete-domain.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundDeleteDomain(MInboundDomain(
        domain = "inbound.example.com"
      )), defaultTimeout)(_ shouldBe Success(
        MInboundDomainResponse(
          domain = "inbound.example.com",
          created_at = "2013-01-01 15:30:27",
          valid_mx = true
        )
      ))
    }
  }

  it should "work getting a valid MInboundDomainResponse" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.inboundDeleteDomain(MInboundDomain(domain = "testingdomain")), defaultTimeout) {
        case Success(res) =>
          res.domain shouldBe "testingdomain"
          res.valid_mx shouldBe false
        case Failure(t) => fail(t)
      }
    }
  }

  "InboundRoutes" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=routes" in {
    withMockClient("/inbound/routes.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundRoutes(MInboundDomain(
        domain = "inbound.example.com"
      )), defaultTimeout)(_ shouldBe Success(List(
        MInboundRouteResponse(
          id = "7.23",
          pattern = "mailbox-*",
          url = "http://example.com/webhook-url"
        )
      )))
    }
  }

  "InboundAddRoute" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=add-route" in {
    withMockClient("/inbound/add-route.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundAddRoute(MInboundRoute(
        domain = "inbound.example.com",
        pattern = "mailbox-*",
        url = "http://example.com/webhook-url"
      )), defaultTimeout)(_ shouldBe Success(
        MInboundRouteResponse(
          id = "7.23",
          pattern = "mailbox-*",
          url = "http://example.com/webhook-url"
        )
      ))
    }
  }

  "InboundUpdateRoute" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=update-route" in {
    withMockClient("/inbound/update-route.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundUpdateRoute(MInboundUpdateRoute(
        id = "7.23",
        pattern = "mailbox-*",
        url = "http://example.com/webhook-url"
      )), defaultTimeout)(_ shouldBe Success(
        MInboundRouteResponse(
          id = "7.23",
          pattern = "mailbox-*",
          url = "http://example.com/webhook-url"
        )
      ))
    }
  }

  "InboundDeleteRoute" should "handle the example at https://www.mandrillapp.com/api/docs/inbound.JSON.html#method=delete-route" in {
    withMockClient("/inbound/delete-route.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.inboundDeleteRoute(MInboundDelRoute(
        id = "7.23"
      )), defaultTimeout)(_ shouldBe Success(
        MInboundRouteResponse(
          id = "7.23",
          pattern = "mailbox-*",
          url = "http://example.com/webhook-url"
        )
      ))
    }
  }

}

