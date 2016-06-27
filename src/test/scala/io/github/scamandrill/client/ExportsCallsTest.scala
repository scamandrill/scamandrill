package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.Success

class ExportsCallsTest extends MandrillSpec {

  "Info" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=info" in {
    withMockClient("/exports/info.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.exportInfo(MExportInfo(
        id = "example id"
      )), defaultTimeout)(_ shouldBe Success(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = utcDateTimeParser("2013-01-01 12:30:28"),
          `type` = "activity",
          finished_at = utcDateTimeParser("2013-01-01 12:35:52").?,
          state = "working",
          result_url = "http://exports.mandrillapp.com/example/export.zip".?
        )
      ))
    }
  }

  "List" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=list" in {
    withMockClient("/exports/list.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.exportList(), defaultTimeout)(_ shouldBe Success(List(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = utcDateTimeParser("2013-01-01 12:30:28"),
          `type` = "activity",
          finished_at = Some(utcDateTimeParser("2013-01-01 12:35:52")),
          state = "working",
          result_url = "http://exports.mandrillapp.com/example/export.zip".?
        )
      )))
    }
  }

  "Rejects" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=rejects" in {
    withMockClient("/exports/rejects.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.exportReject(MExportNotify(
        notify_email = "notify_email@example.com"
      )), defaultTimeout)(_ shouldBe Success(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = utcDateTimeParser("2013-01-01 12:30:28"),
          `type` = "reject",
          finished_at = None,
          state = "waiting",
          result_url = None
        )
      ))
    }
  }

  "Whitelist" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=whitelist" in {
    withMockClient("/exports/whitelist.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.exportWhitelist(MExportNotify(
        notify_email = "notify_email@example.com"
      )), defaultTimeout)(_ shouldBe Success(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = utcDateTimeParser("2013-01-01 12:30:28"),
          `type` = "reject",
          finished_at = None,
          state = "waiting",
          result_url = None
        )
      ))
    }
  }

  "Activity" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=activity" in {
    withMockClient("/exports/activity.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.exportActivity(MExportActivity(
        notify_email = "notify_email@example.com",
        date_from = utcDateTimeParser("2013-01-01 12:53:01"),
        date_to = utcDateTimeParser("2013-01-06 13:42:18"),
        tags = List(
          "example-tag"
        ),
        senders = List(
          "test@example.com"
        ),
        states = List(
          "sent"
        ),
        api_keys = List(
          "ONzNrsmbtNXoIKyfPmjnig"
        )
      )), defaultTimeout)(_ shouldBe Success(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = utcDateTimeParser("2013-01-01 12:30:28"),
          `type` = "reject",
          finished_at = None,
          state = "waiting",
          result_url = None
        )
      ))
    }
  }
}

