package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

class ExportsCallsTest extends MandrillSpec {

  "ExportsInfo" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=info" in {
    withClient("/exports/info.json") { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.exportInfo(MExportInfo(
        id = "example id"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = "2013-01-01 12:30:28",
          `type` = "activity",
          finished_at = "2013-01-01 12:35:52".?,
          state = "working",
          result_url = "http://exports.mandrillapp.com/example/export.zip".?
        )
      ))
    }
  }

  "ExportsList" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=list" in {
    withClient("/exports/list.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.exportList(), defaultTimeout)(_ shouldBe MandrillSuccess(List(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = "2013-01-01 12:30:28",
          `type` = "activity",
          finished_at = "2013-01-01 12:35:52".?,
          state = "working",
          result_url = "http://exports.mandrillapp.com/example/export.zip".?
        )
      )))
    }
  }

  "ExportsRejects" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=rejects" in {
    withClient("/exports/rejects.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.exportReject(MExportNotify(
        notify_email = "notify_email@example.com"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = "2013-01-01 12:30:28",
          `type` = "reject",
          finished_at = None,
          state = "waiting",
          result_url = None
        )
      ))
    }
  }

  "ExportsWhitelist" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=whitelist" in {
    withClient("/exports/whitelist.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.exportWhitelist(MExportNotify(
        notify_email = "notify_email@example.com"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = "2013-01-01 12:30:28",
          `type` = "reject",
          finished_at = None,
          state = "waiting",
          result_url = None
        )
      ))
    }
  }

  "ExportsActivity" should "handle the example at https://www.mandrillapp.com/api/docs/exports.JSON.html#method=activity" in {
    withClient("/exports/activity.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.exportActivity(MExportActivity(
        notify_email = "notify_email@example.com",
        date_from = "2013-01-01 12:53:01",
        date_to = "2013-01-06 13:42:18",
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
      )), defaultTimeout)(_ shouldBe MandrillSuccess(
        MExportResponse(
          id = "2013-01-01 12:20:28.13842",
          created_at = "2013-01-01 12:30:28",
          `type` = "reject",
          finished_at = None,
          state = "waiting",
          result_url = None
        )
      ))
    }
  }
}

