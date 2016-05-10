package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.concurrent.Await

class MessageCallsTest extends MandrillSpec {

  import io.github.scamandrill.MandrillTestUtils._


  "Send" should "work getting a valid List[MSendResponse]" in {
    val res: List[MSendResponse] = Await.result(
      client.messagesSend(MSendMessage(message = validMessage)), DefaultConfig.defaultTimeout
    )
    res.size shouldBe 1
    res.head.status shouldBe "queued"
    res.head.email shouldBe "test@recipient.com"
    res.head.reject_reason shouldBe None
  }

  "SendTemplate" should "work getting a valid List[MSendResponse]" in {
    val res: List[MSendResponse] = Await.result(
      client.messagesSendTemplate(
        MSendTemplateMessage(
          template_name = "testtemplate",
          template_content = List(MVars("first", "example")),
          message = validMessage
        )
      ),
      DefaultConfig.defaultTimeout
    )
    res.size shouldBe 1
    res.head.status shouldBe "queued"
    res.head.email shouldBe "test@recipient.com"
    res.head.reject_reason shouldBe None
  }

  "Search" should "work getting a valid List[MSearchResponse]" in {
    val res = Await.result(client.messagesSearch(validSearch), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

  // Causes Mandrill to 500
  ignore should "work getting a valid List[MSearchResponse] with a TimeSeries" in {
    val res = Await.result(
      client.messagesSearchTimeSeries(validSearchTimeSeries), DefaultConfig.defaultTimeout
    )
    res shouldBe Nil
  }

  "MessageInfo" should "work getting a valid MMessageInfoResponse" ignore {
    val res = Await.result(client.messagesInfo(MMessageInfo(id = idOfMailForInfoTest)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MMessageInfoResponse]
    res._id shouldBe idOfMailForInfoTest
    res.subject shouldBe "subject test"
    res.email shouldBe "test@example.com"
  }

  //  This call doesn't seem to work in the api
  //  "MessageInfo" should "work getting a valid MContentResponse" in {
  //    val res = Await.result(client.content(MMessageInfo(id = idOfMailForInfoTest)), DefaultConfig.defaultTimeout)
  //    res.getClass shouldBe classOf[MMessageInfoResponse]
  //    res._id shouldBe idOfMailForInfoTest
  //    res.subject shouldBe "subject test"
  //    println(res)
  //    //res.email shouldBe "test@example.com"
  //  }
  //  it should "work getting a valid MContentResponse (blocking client)" in {
  //    mandrillBlockingClient.content(MMessageInfo(id = idOfMailForInfoTest)) match {
  //      case Success(res) =>
  //        res.getClass shouldBe classOf[MMessageInfoResponse]
  //        res._id shouldBe idOfMailForInfoTest
  //        res.subject shouldBe "subject test"
  //        //res.email shouldBe "test@example.com"
  //      case Failure(ex) => fail(ex)
  //    }
  //  }

  "Parse" should "work getting a valid MParseResponse" in {
    val res = Await.result(client.messagesParse(MParse(raw_message = """From: sender@example.com""")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MParseResponse]
    res.from_email shouldBe Some("sender@example.com")
  }

  "SendRaw" should "work getting a valid MParseResponse" in {
    val res = Await.result(client.messagesSendRaw(validRawMessage), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }

  "ListSchedule" should "work getting a valid List[MScheduleResponse]" in {
    val res = Await.result(client.messagesListSchedule(MListSchedule(to = "test@recipient.com")), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }
}