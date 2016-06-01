package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Minute, Span}
import play.api.test.WsTestClient

class UserCallsTest extends MandrillSpec with ScalaFutures {
  import scala.concurrent.ExecutionContext.Implicits.global

  "Ping" should "work getting a valid MPingResponse" in {
    withClient("/users/ping.json") { ws =>
      val instance = new MandrillClient(ws, new APIKey())
      whenReady(instance.usersPing, defaultTimeout) { res =>
        res shouldBe MandrillSuccess(MPingResponse("PONG!"))
      }
    }
  }

  "Ping (version 2)" should "work getting a valid MPingResponse" in {
    withClient("/users/ping2.json") { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.usersPing2, defaultTimeout) { res =>
        res shouldBe MandrillSuccess(MPingResponse("PONG!"))
      }
    }
  }

  "Sender" should "work getting a valid List[MSenderDataResponse]" in {
    withClient("/users/senders.json") { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.usersSenders, defaultTimeout) {
        case MandrillSuccess(senders) =>
          senders.head.getClass shouldBe classOf[MSenderDataResponse]
          senders.exists(_.address == "sender.example@mandrillapp.com") shouldBe true
        case MandrillFailure(t) => fail(t)
      }
    }
  }

  "Info" should "work getting a valid MInfoResponse" in {
    withClient("/users/info.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.usersInfo, defaultTimeout) {
        case MandrillSuccess(info) =>
          info.username shouldBe "myusername"
          info.created_at shouldBe "2013-01-01 15:30:27"
          info.hourly_quota shouldBe 42
          info.public_id shouldBe "aaabbbccc112233"
        case MandrillFailure(t) => fail(t)
      }
    }
  }
}
