package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models.{MInfoResponse, MPingResponse, MSenderDataResponse, _}
import org.scalatest.concurrent.PatienceConfiguration.Timeout
import org.scalatest.concurrent.ScalaFutures
import play.api.test.WsTestClient

import scala.collection.mutable
import scala.concurrent.Await
import scala.concurrent.duration._

class UserCallsTest extends MandrillSpec with ScalaFutures {
  import scala.concurrent.ExecutionContext.Implicits.global

  "Ping" should "work getting a valid MPingResponse" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey(DefaultConfig.defaultKeyFromConfig))
      whenReady(instance.usersPing, Timeout(DefaultConfig.defaultTimeout)) { res =>
        res shouldBe MandrillSuccess(MPingResponse("PONG!"))
      }
    }
  }

  "Ping (version 2)" should "work getting a valid MPingResponse" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey(DefaultConfig.defaultKeyFromConfig))
      whenReady(instance.usersPing2, Timeout(DefaultConfig.defaultTimeout)) { res =>
        res shouldBe MandrillSuccess(MPingResponse("PONG!"))
      }
    }
  }

  "Sender" should "work getting a valid List[MSenderDataResponse]" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey(DefaultConfig.defaultKeyFromConfig))
      whenReady(instance.usersSenders, Timeout(DefaultConfig.defaultTimeout)) {
        case MandrillSuccess(senders) =>
          senders.head.getClass shouldBe classOf[MSenderDataResponse]
          senders.exists(_.address == "scamandrill@test.com") shouldBe true
        case MandrillFailure(t) => fail(t)
      }
    }
  }

  "Info" should "work getting a valid MInfoResponse" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey(DefaultConfig.defaultKeyFromConfig))
      whenReady(instance.usersInfo, Timeout(DefaultConfig.defaultTimeout)) {
        case MandrillSuccess(info) =>
          info.username shouldBe "30909130"
          info.created_at shouldBe "2016-05-05 15:25:38.62985"
          info.hourly_quota shouldBe 25
          info.public_id shouldBe "ALLdCj8lhM94O0jL0V3VLQ"
        case MandrillFailure(t) => fail(t)
      }
    }
  }
}
