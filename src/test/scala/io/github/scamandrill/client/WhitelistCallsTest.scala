package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.Success

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
        created_at = "2013-01-01 15:30:32"
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
}
