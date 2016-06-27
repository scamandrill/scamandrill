package io.github.scamandrill.client

import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.{Failure, Success}

class WhitelistCallsTest extends MandrillSpec {

  "WhitelistAdd" should "handle the example at https://mandrillapp.com/api/docs/whitelists.JSON.html#method=add" in {
    withMockClient("/whitelists/add.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.whitelistAdd(MWhitelistAdd(
        email = "email@example.com",
        comment = "Internal support address".?
      )), defaultTimeout)(_ shouldBe Success(MWhitelistAddResponse(
        email = "example email",
        added = true
      )))
    }
  }

  "WhitelistList" should "handle the example at https://mandrillapp.com/api/docs/whitelists.JSON.html#method=list" in {
    withMockClient("/whitelists/list.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.whitelistList(MWhitelist(
        email = "example email"
      )), defaultTimeout)(_ shouldBe Success(List(MWhitelistListResponse(
        email = "example email",
        detail = "Whitelisted internal address",
        created_at = utcDateTimeParser("2013-01-01 15:30:32")
      ))))
    }
  }

  "WhitelistDelete" should "handle the example at https://mandrillapp.com/api/docs/whitelists.JSON.html#method=delete" in {
    withMockClient("/whitelists/delete.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.whitelistDelete(MWhitelist(
        email = "email@example.com"
      )), defaultTimeout)(_ shouldBe Success(MWhitelistDeleteResponse(
        email = "email@example.com",
        deleted = true
      )))
    }
  }

  import io.github.scamandrill.MandrillTestUtils.whitelistAddress
  "Actual whitelist calls" should "add an email to the whitelist" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.whitelistAdd(MWhitelistAdd(email = whitelistAddress, Some("This is for testing"))), defaultTimeout) {
        case Success(res) =>
          res.added shouldBe true
          res.email shouldBe whitelistAddress
        case Failure(t) => fail(t)
      }
    }
  }

  it should "find that email in the whitelist" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.whitelistList(MWhitelist(email = whitelistAddress)), defaultTimeout) {
        case Success(res) =>
          res.exists(_.email == whitelistAddress) shouldBe true
        case Failure(t) => fail(t)
      }
    }
  }

  it should "remove that email from the whitelist" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.whitelistDelete(MWhitelist(email = whitelistAddress)), defaultTimeout) {
        case Success(res) =>
          res.deleted shouldBe true
          res.email shouldBe whitelistAddress
        case Failure(t) => fail(t)
      }
    }
  }
}
