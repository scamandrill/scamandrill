package io.github.scamandrill.client

import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.models._
import org.scalatest.concurrent.ScalaFutures

import scala.util.{Failure, Success}

class UserCallsTest extends MandrillSpec with ScalaFutures {

  "Ping" should "work getting a valid MPingResponse" in {
    withMockClient("/users/ping.json") { ws =>
      val instance = new MandrillClient(ws)
      whenReady(instance.usersPing, defaultTimeout) { res =>
        res shouldBe Success(MPingResponse("PONG!"))
      }
    }
  }

  "Ping" should "handle an error response from Mandrill" in {
    withMockClient("/users/ping.json", returnError = true) { ws =>
      val instance = new MandrillClient(ws)
      whenReady(instance.usersPing, defaultTimeout){
        case Failure(e:MandrillResponseException) =>
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
    withMockClient("/users/ping.json", raiseException = true) { ws =>
      val instance = new MandrillClient(ws)
      whenReady(instance.usersPing, defaultTimeout)(_ shouldBe a[Failure[_]])
    }
  }

  it should "handle calling the actual mandrillapp.com with a valid key" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.usersPing, defaultTimeout)(_ shouldBe Success(MPingResponse(PING = "PONG!")))
    }
  }

  it should "handle calling the actual mandrillapp.com with an invalid key" taggedAs ActualAPICall in {
    whenReady(scamandrill.getClient("INVALID_KEY").usersPing, defaultTimeout)(_ shouldBe a[Failure[_]])
  }

  "Ping (version 2)" should "work getting a valid MPingResponse" in {
    withMockClient("/users/ping2.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.usersPing2, defaultTimeout) { res =>
        res shouldBe Success(MPingResponse("PONG!"))
      }
    }
  }

  "Sender" should "work getting a valid List[MSenderDataResponse]" in {
    withMockClient("/users/senders.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.usersSenders, defaultTimeout) {
        case Success(senders) =>
          senders.head.getClass shouldBe classOf[MSenderDataResponse]
          senders.exists(_.address == "sender.example@mandrillapp.com") shouldBe true
        case Failure(t) => fail(t)
      }
    }
  }

  "Info" should "work getting a valid MInfoResponse" in {
    withMockClient("/users/info.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.usersInfo, defaultTimeout) {
        case Success(info) =>
          info.username shouldBe "myusername"
          info.created_at shouldBe "2013-01-01 15:30:27"
          info.hourly_quota shouldBe 42
          info.public_id shouldBe "aaabbbccc112233"
        case Failure(t) => fail(t)
      }
    }
  }
}
