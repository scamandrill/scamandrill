package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Minute, Span}
import play.api.test.WsTestClient

class UserCallsTest extends MandrillSpec with ScalaFutures {
  import scala.concurrent.ExecutionContext.Implicits.global

  "Ping" should "work getting a valid MPingResponse" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.usersPing, defaultTimeout) { res =>
        res shouldBe MandrillSuccess(MPingResponse("PONG!"))
      }
    }
  }

  "Ping (version 2)" should "work getting a valid MPingResponse" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.usersPing2, defaultTimeout) { res =>
        res shouldBe MandrillSuccess(MPingResponse("PONG!"))
      }
    }
  }

  "Sender" should "work getting a valid List[MSenderDataResponse]" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.usersSenders, defaultTimeout) {
        case MandrillSuccess(senders) =>
          senders.head.getClass shouldBe classOf[MSenderDataResponse]
          senders.exists(_.address == "scamandrill@test.com") shouldBe true
        case MandrillFailure(t) => fail(t)
      }
    }
  }

  "Info" should "work getting a valid MInfoResponse" in {
    WsTestClient.withClient { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.usersInfo, defaultTimeout) {
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
