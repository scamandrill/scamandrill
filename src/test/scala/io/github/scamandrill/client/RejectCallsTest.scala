package io.github.scamandrill.client

import java.util.UUID

import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.models.{MRejectAdd, MRejectAddResponse, _}
import io.github.scamandrill.client.implicits._

import scala.util.{Failure, Success}

class RejectCallsTest extends MandrillSpec {

  "RejectAdd" should "handle the example at https://mandrillapp.com/api/docs/rejects.JSON.html#method=add" in {
    withMockClient("/rejects/add.json"){ wc =>
      val instance = new MandrillClient(wc)
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
    withMockClient("/rejects/list.json"){ wc =>
      val instance = new MandrillClient(wc)
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
    withMockClient("/rejects/delete.json"){ wc =>
      val instance = new MandrillClient(wc)
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
  val testEmail = s"${UUID.randomUUID().toString}@test.com"
  "Actual Reject Calls" should "add an address to the reject list" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.rejectAdd(MRejectAdd(email = testEmail)), defaultTimeout)(_ shouldBe Success(MRejectAddResponse(
        email = testEmail, added = true
      )))
    }
  }

  it should "find that email in the reject list" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.rejectList(MRejectList(email = testEmail)), defaultTimeout) {
        case Success(res) =>
          res.exists(_.email == testEmail) shouldBe true
        case Failure(t) => fail(t)
      }
    }
  }

  it should "delete that email from the reject list" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.rejectDelete(MRejectDelete(email = testEmail)), defaultTimeout)(_ shouldBe Success(MRejectDeleteResponse(
        email = testEmail, deleted = true, subaccount = None
      )))
    }
  }
}
