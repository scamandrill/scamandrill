package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._

import io.github.scamandrill.MandrillTestUtils._

import scala.util.Failure
import scala.util.Success

class UrlCallsTest extends MandrillSpec {

  "UrlsList" should "work getting a valid List[MUrlResponse] (async client)" in {
    val res = Await.result(mandrillAsyncClient.urlsList(MKey()), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

  "UrlsSearch" should "work getting a valid List[MUrlResponse] (async client)" in {
    val res = Await.result(mandrillAsyncClient.urlsSearch(MUrlSearch(q = "http://example.com/example")), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

//  "UrlsTimeSeries" should "work getting a valid List[MUrlTimeResponse] (async client)" in {
//    val res = Await.result(mandrillAsyncClient.urlsTimeSeries(MUrlTimeSeries(key = "http://example.com/example")), DefaultConfig.defaultTimeout)
//    res shouldBe Nil
//  }
//  it should "work getting a valid List[MUrlTimeResponse] (blocking client)" in {
//    mandrillBlockingClient.urlsTimeSeries(MUrlTimeSeries(key = "http://example.com/example")) match {
//      case Success(res) =>
//        res shouldBe Nil
//      case Failure(ex) => fail(ex)
//    }
//  }

  "UrlsAddTrackingDomain" should "work getting a valid MUrlDomainResponse (async client)" in {
    val res = Await.result(mandrillAsyncClient.urlsAddTrackingDomain(MUrlDomain(domain = "test.com")), DefaultConfig.defaultTimeout)
    res.domain shouldBe "test.com"
  }

  "UrlsTrackingDomain" should "work getting a valid List[MUrlDomainResponse] (async client)" in {
    val res = Await.result(mandrillAsyncClient.urlsTrackingDomain(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MUrlDomainResponse]
    res.head.domain shouldBe "test.com"
  }

  "UrlsCheckTrackingDomain" should "work getting a valid List[MUrlDomainResponse] (async client)" in {
    val res = Await.result(mandrillAsyncClient.urlsCheckTrackingDomain(MUrlDomain(domain= "test.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MUrlDomainResponse]
    res.domain shouldBe "test.com"
  }
}
