package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.util.Success

class TagsCallsTest extends MandrillSpec {

  "TagList" should "handle the example at https://mandrillapp.com/api/docs/tags.JSON.html#method=list" in {
    withMockClient("/tags/list.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.tagList(), defaultTimeout)(_ shouldBe Success(List(MTagResponse(
        tag = "example-tag",
        reputation = 42,
        sent = 42,
        hard_bounces = 42,
        soft_bounces = 42,
        rejects = 42,
        complaints = 42,
        unsubs = 42,
        opens = 42,
        clicks = 42,
        unique_opens = 42,
        unique_clicks = 42
      ))))
    }
  }

    "TagDelete" should "handle the example at https://mandrillapp.com/api/docs/tags.JSON.html#method=delete" in {
      withMockClient("/tags/delete.json"){ wc =>
        val instance = new MandrillClient(wc)
        whenReady(instance.tagDelete(MTagRequest(tag="example-tag")), defaultTimeout)(_ shouldBe Success(
          MTagResponse(
            tag = "example-tag",
            reputation = 42,
            sent = 42,
            hard_bounces = 42,
            soft_bounces = 42,
            rejects = 42,
            complaints = 42,
            unsubs = 42,
            opens = 42,
            clicks = 42,
            unique_opens = 42,
            unique_clicks = 42
          )
        ))
      }
    }

  "TagInfo" should "handle the example at https://mandrillapp.com/api/docs/tags.JSON.html#method=info" in {
    withMockClient("/tags/info.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.tagInfo(MTagRequest(tag="example-tag")), defaultTimeout)(_ shouldBe Success(
        MTagInfoResponse(
          tag = "example-tag",
          sent = 42,
          hard_bounces = 42,
          soft_bounces = 42,
          rejects = 42,
          complaints = 42,
          unsubs = 42,
          opens = 42,
          clicks = 42,
          stats = MStats(
            today = MStat(
              sent = 42,
              hard_bounces = 42,
              soft_bounces = 42,
              rejects = 42,
              complaints = 42,
              unsubs = 42,
              opens = 42,
              unique_opens = 42,
              clicks = 42,
              unique_clicks = 42
            ),
            last_7_days = MStat(
              sent = 42,
              hard_bounces = 42,
              soft_bounces = 42,
              rejects = 42,
              complaints = 42,
              unsubs = 42,
              opens = 42,
              unique_opens = 42,
              clicks = 42,
              unique_clicks = 42
            ),
            last_30_days = MStat(
              sent = 42,
              hard_bounces = 42,
              soft_bounces = 42,
              rejects = 42,
              complaints = 42,
              unsubs = 42,
              opens = 42,
              unique_opens = 42,
              clicks = 42,
              unique_clicks = 42
            ),
            last_60_days = MStat(
              sent = 42,
              hard_bounces = 42,
              soft_bounces = 42,
              rejects = 42,
              complaints = 42,
              unsubs = 42,
              opens = 42,
              unique_opens = 42,
              clicks = 42,
              unique_clicks = 42
            ),
            last_90_days = MStat(
              sent = 42,
              hard_bounces = 42,
              soft_bounces = 42,
              rejects = 42,
              complaints = 42,
              unsubs = 42,
              opens = 42,
              unique_opens = 42,
              clicks = 42,
              unique_clicks = 42
            )
          )
        )
      ))
    }
  }

  "TagTimeSeries" should "handle the example at https://mandrillapp.com/api/docs/tags.JSON.html#method=time-series" in {
    withMockClient("/tags/time-series.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.tagTimeSeries(MTagRequest(tag="example-tag")), defaultTimeout)(_ shouldBe Success(List(
        MTimeSeriesResponse(
          time = "2013-01-01 15:00:00",
          sent = 42,
          hard_bounces = 42,
          soft_bounces = 42,
          rejects = 42,
          complaints = 42,
          unsubs = Some(42),
          opens = 42,
          unique_opens = 42,
          clicks = 42,
          unique_clicks = 42
        )
      )))
    }
  }

  "TagAllTimeSeries" should "handle the example at https://mandrillapp.com/api/docs/tags.JSON.html#method=all-time-series" in {
    withMockClient("/tags/all-time-series.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.tagAllTimeSeries(), defaultTimeout)(_ shouldBe Success(List(
        MTimeSeriesResponse(
          time = "2013-01-01 15:00:00",
          sent = 42,
          hard_bounces = 42,
          soft_bounces = 42,
          rejects = 42,
          complaints = 42,
          unsubs = Some(42),
          opens = 42,
          unique_opens = 42,
          clicks = 42,
          unique_clicks = 42
        )
      )))
    }
  }
}
