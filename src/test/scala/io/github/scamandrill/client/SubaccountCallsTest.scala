package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._


class SubaccountCallsTest extends MandrillSpec {

  "SubaccountAdd" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=add" in {
    withClient("/subaccounts/add.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.subaccountAdd(MSubaccount(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        notes = "Free plan user, signed up on 2013-01-01 12:00:00",
        custom_quota = 42
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = "2013-01-01 15:30:27",
        first_sent_at = "2013-01-01 15:30:29".?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountPause" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=pause" in {
    withClient("/subaccounts/pause.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.subaccountPause(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = "2013-01-01 15:30:27",
        first_sent_at = "2013-01-01 15:30:29".?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountResume" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=resume" in {
    withClient("/subaccounts/resume.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.subaccountResume(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = "2013-01-01 15:30:27",
        first_sent_at = "2013-01-01 15:30:29".?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountUpdate" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=update" in {
    withClient("/subaccounts/update.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.subaccountUpdate(MSubaccount(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        notes = "Free plan user, signed up on 2013-01-01 12:00:00",
        custom_quota = 42
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = "2013-01-01 15:30:27",
        first_sent_at = "2013-01-01 15:30:29".?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountInfo" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=info" in {
    withClient("/subaccounts/info.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.subaccountInfo(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSubaccountsInfoResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        notes = "Free plan user, signed up on 2013-01-01 12:00:00",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = "2013-01-01 15:30:27",
        first_sent_at = "2013-01-01 15:30:29".?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42,
        sent_hourly = 42,
        hourly_quota = 42,
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
        )
      )))
    }
  }

  "SubaccountList" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=list" in {
    withClient("/subaccounts/list.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.subaccountList(MSubaccountList(
        q = "cust-1"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(List(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = "2013-01-01 15:30:27",
        first_sent_at = "2013-01-01 15:30:29".?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      ))))
    }
  }

  "SubaccountDelete" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=delete" in {
    withClient("/subaccounts/delete.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.subaccountDelete(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = "2013-01-01 15:30:27",
        first_sent_at = "2013-01-01 15:30:29".?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }
}
