package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.concurrent.Await

class UrlCallsTest extends MandrillSpec {

  "UrlsList" should "work getting a valid List[MUrlResponse]" in {
    val res = Await.result(client.urlsList(MKey()), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

  "UrlsSearch" should "work getting a valid List[MUrlResponse]" in {
    val res = Await.result(client.urlsSearch(MUrlSearch(q = "http://example.com/example")), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

  //  "UrlsTimeSeries" should "work getting a valid List[MUrlTimeResponse]" in {
  //    val res = Await.result(client.urlsTimeSeries(MUrlTimeSeries(key = "http://example.com/example")), DefaultConfig.defaultTimeout)
  //    res shouldBe Nil
  //  }
  //  it should "work getting a valid List[MUrlTimeResponse] (blocking client)" in {
  //    mandrillBlockingClient.urlsTimeSeries(MUrlTimeSeries(key = "http://example.com/example")) match {
  //      case Success(res) =>
  //        res shouldBe Nil
  //      case Failure(ex) => fail(ex)
  //    }
  //  }

  "UrlsAddTrackingDomain" should "work getting a valid MUrlDomainResponse" in {
    val res = Await.result(client.urlsAddTrackingDomain(MUrlDomain(domain = "test.com")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "test.com"
  }

  "UrlsTrackingDomain" should "work getting a valid List[MUrlDomainResponse]" in {
    val res = Await.result(client.urlsTrackingDomain(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MUrlDomainResponse]
    res.head.domain shouldBe "test.com"
  }

  "UrlsCheckTrackingDomain" should "work getting a valid List[MUrlDomainResponse]" in {
    val res = Await.result(client.urlsCheckTrackingDomain(MUrlDomain(domain = "test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MUrlDomainResponse]
    res.domain shouldBe "test.com"
  }
}
