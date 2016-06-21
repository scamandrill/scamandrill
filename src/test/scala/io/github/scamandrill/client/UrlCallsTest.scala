package io.github.scamandrill.client

import io.github.scamandrill.MandrillTestUtils._
import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.{Failure, Success}

class UrlCallsTest extends MandrillSpec {

  "UrlsList" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=list" in {
    withMockClient("/urls/list.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.urlsList(), defaultTimeout)(_ shouldBe Success(List(MUrlResponse(
        url = "http://example.com/example-page",
        sent = 42,
        clicks = 42,
        unique_clicks = 42
      ))))
    }
  }

  "UrlsSearch" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=search" in {
    withMockClient("/urls/search.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.urlsSearch(MUrlSearch(
        q = "http://example.com/example"
      )), defaultTimeout)(_ shouldBe Success(List(MUrlResponse(
        url = "http://example.com/example-page",
        sent = 42,
        clicks = 42,
        unique_clicks = 42
      ))))
    }
  }

  "UrlsTimeSeries" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=time-series" in {
    withMockClient("/urls/time-series.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.urlsTimeSeries(MUrlTimeSeries(
        url = "http://example.com/example-page"
      )), defaultTimeout)(_ shouldBe Success(List(MUrlTimeResponse(
        time = "2013-01-01 15:00:00",
        sent = 42,
        clicks = 42,
        unique_clicks = 42
      ))))
    }
  }

  "UrlsAddTrackingDomain" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=tracking-domains" in {
    withMockClient("/urls/add-tracking-domain.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.urlsAddTrackingDomain(MUrlDomain(
        domain = "track.example.com"
      )), defaultTimeout)(_ shouldBe Success(MUrlDomainResponse(
        domain = "example.com",
        created_at = "2013-01-01 15:30:27",
        last_tested_at = "2013-01-01 15:40:42",
        cname = MUrlCname(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        valid_tracking = true
      )))
    }
  }

  "UrlsTrackingDomain" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=add-tracking-domain" in {
    withMockClient("/urls/tracking-domains.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.urlsTrackingDomain(), defaultTimeout)(_ shouldBe Success(List(MUrlDomainResponse(
        domain = "example.com",
        created_at = "2013-01-01 15:30:27",
        last_tested_at = "2013-01-01 15:40:42",
        cname = MUrlCname(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        valid_tracking = true
      ))))
    }
  }

  "UrlsCheckTrackingDomain" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=check-tracking-domain" in {
    withMockClient("/urls/check-tracking-domain.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.urlsCheckTrackingDomain(MUrlDomain(
        domain = "track.example.com"
      )), defaultTimeout)(_ shouldBe Success(MUrlDomainResponse(
        domain = "example.com",
        created_at = "2013-01-01 15:30:27",
        last_tested_at = "2013-01-01 15:40:42",
        cname = MUrlCname(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        valid_tracking = true
      )))
    }
  }

  "Actual url calls" should "get an empty list" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.urlsList(), defaultTimeout)(_ shouldBe Success(Nil))
    }
  }

  it should "get an empty list for a query" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.urlsSearch(MUrlSearch(q = "http://example.com/example")), defaultTimeout)(_ shouldBe Success(Nil))
    }
  }

  import io.github.scamandrill.MandrillTestUtils.testTrackingDomain
  it should "add a tracking domain" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.urlsAddTrackingDomain(MUrlDomain(domain = testTrackingDomain)), defaultTimeout) {
        case Success(res) => res.domain
        case Failure(t) => fail(t)
      }
    }
  }

  it should "find that tracking domain in a list" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.urlsTrackingDomain(), defaultTimeout) {
        case Success(res) => res.exists(_.domain == testTrackingDomain) shouldBe true
        case Failure(t) => fail(t)
      }
    }
  }

  it should "check that tracking domain" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.urlsCheckTrackingDomain(MUrlDomain(domain = testTrackingDomain)), defaultTimeout) {
        case Success(res) => res.domain shouldBe testTrackingDomain
        case Failure(t) => fail(t)
      }
    }
  }
}
