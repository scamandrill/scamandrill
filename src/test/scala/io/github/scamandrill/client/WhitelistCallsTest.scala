package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.concurrent.Await

class WhitelistCallsTest extends MandrillSpec {

  "WhitelistAdd" should "work getting a valid MWhitelistAddResponse" in {
    val res = Await.result(client.whitelistAdd(MWhitelist(email = "whitelist@example.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MWhitelistAddResponse]
    res shouldBe MWhitelistAddResponse("whitelist@example.com",true)
  }

  "WhitelistList" should "work getting a valid List[MWhitelistDeleteResponse]" in {
    val res = Await.result(client.whitelistList(MWhitelist(email = "whitelist@example.com")), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MWhitelistListResponse]
    res.head.email shouldBe "whitelist@example.com"
  }

  "WhitelistDelete" should "work getting a valid MWhitelistDeleteResponse" in {
    val res = Await.result(client.whitelistDelete(MWhitelist(email = "whitelist@example.com")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MWhitelistDeleteResponse]
    res shouldBe MWhitelistDeleteResponse("whitelist@example.com",true)
  }
}
