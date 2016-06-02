package io.github.scamandrill.client

import io.github.scamandrill.{MandrillSpec, client}
import io.github.scamandrill.models._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Minute, Span}
import play.api.test.WsTestClient

class UserCallsTest extends MandrillSpec with ScalaFutures {

  "Ping" should "work getting a valid MPingResponse" in {
    withClient("/users/ping.json") { ws =>
      val instance = new MandrillClient(ws, new APIKey())
      whenReady(instance.usersPing, defaultTimeout) { res =>
        res shouldBe MandrillSuccess(MPingResponse("PONG!"))
      }
    }
  }

  "Ping" should "handle an error response from Mandrill" in {
    withClient("/users/ping.json", returnError = true) { ws =>
      val instance = new MandrillClient(ws, new APIKey())
      whenReady(instance.usersPing, defaultTimeout){
        case MandrillFailure(e:MandrillResponseException) =>
          e.mandrillError shouldBe MandrillError(
            status = "error",
            code = -1,
            name = "Invalid_Key",
            message = "Invalid API key"
          )
        case _ => fail("We should get a mandrill failure with a MandrillResponseException")
      }
    }
  }

  it should "handle an unexpected exception whilst calling Mandrill" in {
    withClient("/users/ping.json", raiseException = true) { ws =>
      val instance = new MandrillClient(ws, new APIKey())
      whenReady(instance.usersPing, defaultTimeout)(_ shouldBe a[MandrillFailure[_]])
    }
  }

  it should "handle calling the actual mandrillapp.com with a valid key" in {
    assume(SCAMANDRILL_API_KEY.isDefined)
    val scamandrill = Scamandrill()
    try {
      SCAMANDRILL_API_KEY.foreach { apiKey =>
        whenReady(scamandrill.getClient(apiKey).usersPing, defaultTimeout)(_ shouldBe MandrillSuccess(MPingResponse(PING = "PONG!")))
      }
    } finally {
      scamandrill.shutdown()
    }
  }

  it should "handle calling the actual mandrillapp.com with an invalid key" in {
    assume(SCAMANDRILL_API_KEY.isDefined)
    val scamandrill = Scamandrill()
    try {
      SCAMANDRILL_API_KEY.foreach { apiKey =>
        whenReady(scamandrill.getClient("").usersPing, defaultTimeout)(_ shouldBe a[MandrillFailure[_]])
      }
    } finally {
      scamandrill.shutdown()
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
