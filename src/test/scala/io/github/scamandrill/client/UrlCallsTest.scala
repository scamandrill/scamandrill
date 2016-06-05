package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.Success

class UrlCallsTest extends MandrillSpec {

  "UrlsList" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=list" in {
    withClient("/urls/list.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.urlsList(), defaultTimeout)(_ shouldBe Success(List(MUrlResponse(
        url = "http://example.com/example-page",
        sent = 42,
        clicks = 42,
        unique_clicks = 42
      ))))
    }
  }

  "UrlsSearch" should "handle the example at https://www.mandrillapp.com/api/docs/urls.JSON.html#method=search" in {
    withClient("/urls/search.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
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
    withClient("/urls/time-series.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
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
    withClient("/urls/add-tracking-domain.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
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
    withClient("/urls/tracking-domains.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
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
    withClient("/urls/check-tracking-domain.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
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
}
