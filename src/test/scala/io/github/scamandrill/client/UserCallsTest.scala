package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models.{MInfoResponse, MPingResponse, MSenderDataResponse, _}

import scala.concurrent.Await

class UserCallsTest extends MandrillSpec {

  "Ping" should "work getting a valid MPingResponse" in {
    val res = Await.result(client.usersPing, DefaultConfig.defaultTimeout)
    res shouldBe MPingResponse("\"PONG!\"")
  }

  "Ping (version 2)" should "work getting a valid MPingResponse" in {
    val res1 = Await.result(client.usersPing2, DefaultConfig.defaultTimeout)
    res1 shouldBe MPingResponse("PONG!")
  }

  "Sender" should "work getting a valid List[MSenderDataResponse]" in {
    val res = Await.result(client.usersSenders, DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MSenderDataResponse]
    res.exists(_.address == "scamandrill@test.com") shouldBe true
  }

  "Info" should "work getting a valid MInfoResponse" in {
    val res: MInfoResponse = Await.result(client.usersInfo, DefaultConfig.defaultTimeout)
    res.username shouldBe "30909130"
    res.created_at shouldBe "2016-05-05 15:25:38.62985"
    res.hourly_quota shouldBe 25
    res.public_id shouldBe "ALLdCj8lhM94O0jL0V3VLQ"
  }
}
