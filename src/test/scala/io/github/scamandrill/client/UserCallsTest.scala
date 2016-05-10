package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._

import scala.util.{Failure, Success, Try}
import io.github.scamandrill.MandrillTestUtils._
import io.github.scamandrill.models.MSenderDataResponse
import io.github.scamandrill.models.MInfoResponse
import io.github.scamandrill.models.MKey
import io.github.scamandrill.models.MPingResponse

class UserCallsTest extends MandrillSpec {

  "Ping" should "work getting a valid MPingResponse (async client)" in {
    val res = Await.result(client.usersPing(MKey()), DefaultConfig.defaultTimeout)
    res shouldBe MPingResponse("\"PONG!\"")
  }

  "Ping (version 2)" should "work getting a valid MPingResponse" in {
    val res1 = Await.result(client.usersPing2(MKey()), DefaultConfig.defaultTimeout)
    res1 shouldBe MPingResponse("PONG!")
  }

  "Sender" should "work getting a valid List[MSenderDataResponse] (async client)" in {
    val res = Await.result(client.usersSenders(MKey()), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MSenderDataResponse]
    res.exists(_.address == "scamandrill@test.com") shouldBe true
  }

  "Info" should "work getting a valid MInfoResponse (async client)" in {
    val res: MInfoResponse = Await.result(client.usersInfo(MKey()), DefaultConfig.defaultTimeout)
    res.username shouldBe "30909130"
    res.created_at shouldBe "2016-05-05 15:25:38.62985"
    res.hourly_quota shouldBe 25
    res.public_id shouldBe "ALLdCj8lhM94O0jL0V3VLQ"
  }
}
