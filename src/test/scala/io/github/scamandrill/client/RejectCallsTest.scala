package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models.{MRejectAdd, MRejectAddResponse, _}
import io.github.scamandrill.client.implicits._

import scala.util.Success

class RejectCallsTest extends MandrillSpec {

  "RejectAdd" should "handle the example at https://mandrillapp.com/api/docs/rejects.JSON.html#method=add" in {
    withClient("/rejects/add.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.rejectAdd(MRejectAdd(
        email = "example email",
        comment = "example comment".?,
        subaccount = "cust-123".?
      )), defaultTimeout)(_ shouldBe Success(MRejectAddResponse(
        added = true,
        email = "example email"
      )))
    }
  }

  "RejectList" should "handle the example at https://mandrillapp.com/api/docs/rejects.JSON.html#method=list" in {
    withClient("/rejects/list.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.rejectList(MRejectList(
        email = "example email",
        include_expired = true,
        subaccount = "cust-123".?
      )), defaultTimeout)(_ shouldBe Success(List(MRejectListResponse(
        email = "example email",
        reason = "hard-bounce",
        detail = "550 mailbox does not exist".?,
        created_at = "2013-01-01 15:30:27",
        last_event_at = "2013-01-01 15:30:27",
        expires_at = "2013-01-01 15:30:49".?,
        expired = false,
        sender = Some(MSenderDataResponse(
          address = "sender.example@mandrillapp.com",
          created_at = "2013-01-01 15:30:27",
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
        )),
        subaccount = "example subaccount".?
      ))))
    }
  }

  "RejectDelete" should "handle the example at https://mandrillapp.com/api/docs/rejects.JSON.html#method=delete" in {
    withClient("/rejects/delete.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.rejectDelete(MRejectDelete(
        email = "example email",
        subaccount = "cust-123".?
      )), defaultTimeout)(_ shouldBe Success(MRejectDeleteResponse(
        email = "email@example.com",
        deleted = true,
        subaccount = "cust-123".?
      )))
    }
  }

}
