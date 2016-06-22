package io.github.scamandrill.client

import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.{Failure, Success}

class SubaccountCallsTest extends MandrillSpec {

  "SubaccountAdd" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=add" in {
    withMockClient("/subaccounts/add.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.subaccountAdd(MSubaccount(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        notes = "Free plan user, signed up on 2013-01-01 12:00:00",
        custom_quota = 42
      )), defaultTimeout)(_ shouldBe Success(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = utcDateTimeParser("2013-01-01 15:30:27"),
        first_sent_at = utcDateTimeParser("2013-01-01 15:30:29").?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountPause" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=pause" in {
    withMockClient("/subaccounts/pause.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.subaccountPause(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe Success(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = utcDateTimeParser("2013-01-01 15:30:27"),
        first_sent_at = utcDateTimeParser("2013-01-01 15:30:29").?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountResume" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=resume" in {
    withMockClient("/subaccounts/resume.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.subaccountResume(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe Success(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = utcDateTimeParser("2013-01-01 15:30:27"),
        first_sent_at = utcDateTimeParser("2013-01-01 15:30:29").?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountUpdate" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=update" in {
    withMockClient("/subaccounts/update.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.subaccountUpdate(MSubaccount(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        notes = "Free plan user, signed up on 2013-01-01 12:00:00",
        custom_quota = 42
      )), defaultTimeout)(_ shouldBe Success(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = utcDateTimeParser("2013-01-01 15:30:27"),
        first_sent_at = utcDateTimeParser("2013-01-01 15:30:29").?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  "SubaccountInfo" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=info" in {
    withMockClient("/subaccounts/info.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.subaccountInfo(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe Success(MSubaccountsInfoResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        notes = "Free plan user, signed up on 2013-01-01 12:00:00",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = utcDateTimeParser("2013-01-01 15:30:27"),
        first_sent_at = utcDateTimeParser("2013-01-01 15:30:29").?,
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
    withMockClient("/subaccounts/list.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.subaccountList(MSubaccountList(
        q = "cust-1"
      )), defaultTimeout)(_ shouldBe Success(List(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = utcDateTimeParser("2013-01-01 15:30:27"),
        first_sent_at = utcDateTimeParser("2013-01-01 15:30:29").?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      ))))
    }
  }

  "SubaccountDelete" should "handle the example at https://www.mandrillapp.com/api/docs/subaccounts.JSON.html#method=delete" in {
    withMockClient("/subaccounts/delete.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.subaccountDelete(MSubaccountInfo(
        id = "cust-123"
      )), defaultTimeout)(_ shouldBe Success(MSubaccountsResponse(
        id = "cust-123",
        name = "ABC Widgets, Inc.",
        custom_quota = 42,
        status = "active",
        reputation = 42,
        created_at = utcDateTimeParser("2013-01-01 15:30:27"),
        first_sent_at = utcDateTimeParser("2013-01-01 15:30:29").?,
        sent_weekly = 42,
        sent_monthly = 42,
        sent_total = 42
      )))
    }
  }

  import io.github.scamandrill.MandrillTestUtils.validSubaccount

  "Actual subaccount calls" should "add a subaccount" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.subaccountAdd(validSubaccount), defaultTimeout) {
        case Success(res) =>
          res.id shouldBe validSubaccount.id
          res.name shouldBe validSubaccount.name
          res.status shouldBe "active"
        case Failure(t) => fail(t)
      }
    }
  }

  it should "pause that account" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.subaccountPause(MSubaccountInfo(id = validSubaccount.id)), defaultTimeout) {
        case Success(res) =>
          res.id shouldBe validSubaccount.id
          res.name shouldBe validSubaccount.name
          res.status shouldBe "paused"
        case Failure(t) => fail(t)
      }
    }
  }

  it should "resume that account" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.subaccountResume(MSubaccountInfo(id = validSubaccount.id)), defaultTimeout) {
        case Success(res) =>
          res.id shouldBe validSubaccount.id
          res.name shouldBe validSubaccount.name
          res.status shouldBe "active"
        case Failure(t) => fail(t)
      }
    }
  }

  it should "update that account" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.subaccountUpdate(validSubaccount.copy(notes = "updated")), defaultTimeout) {
        case Success(res) =>
          res.id shouldBe validSubaccount.id
          res.name shouldBe validSubaccount.name
          res.status shouldBe "active"
        case Failure(t) => fail(t)
      }
    }
  }

  it should "get info about that account" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.subaccountInfo(MSubaccountInfo(id = validSubaccount.id)), defaultTimeout) {
        case Success(res) =>
          res.id shouldBe validSubaccount.id
          res.name shouldBe validSubaccount.name
          res.status shouldBe "active"
        case Failure(t) => fail(t)
      }
    }
  }

  it should "find that account in the list" in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.subaccountList(MSubaccountList(q = "test")), defaultTimeout) {
        case Success(res) => res.exists(_.id == validSubaccount.id) shouldBe true
        case Failure(t) => fail(t)
      }
    }
  }

  it should "delete that account" in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.subaccountDelete(MSubaccountInfo(id = validSubaccount.id)), defaultTimeout) {
        case Success(res) =>
          res.id shouldBe validSubaccount.id
          res.name shouldBe validSubaccount.name
        case Failure(t) => fail(t)
      }
    }
  }
}
