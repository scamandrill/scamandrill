package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import org.scalatest.concurrent.ScalaFutures

import scala.concurrent.Await

class MessageCallsTest extends MandrillSpec with ScalaFutures {

  import scala.concurrent.ExecutionContext.Implicits.global

  "Send" should "work getting a valid List[MSendResponse]" in {
    withClient("/messages/send.json") { ws =>
      val instance = new MandrillClient(ws, new APIKey())
      whenReady(instance.messagesSend(MSendMessage(
        async = false,
        ip_pool = Some("Main Pool"),
        message = new MSendMsg(
          html = "<p>Example HTML content</p>",
          text = "Example text content",
          subject = "example subject",
          from_email = "message.from_email@example.com",
          from_name = "Example Name",
          to = List(MTo(
            email = "recipient.email@example.com",
            name = Some("Recipient Name")
          )),
          headers = Some(List(MHeader("Reply-To", "message.reply@example.com"))),
          important = false,
          bcc_address = Some("message.bcc_address@example.com"),
          merge = true,
          global_merge_vars = List(MVars("merge1", "merge1 content")),
          merge_vars = List(MMergeVars("recipient.email@example.com", List(MVars("merge2", "merge2 content")))),
          tags = List("password-resets"),
          subaccount = Some("customer-123"),
          metadata = List(MMetadata("website", "www.example.com")),
          recipient_metadata = List(MRecipientMetadata("recipient.email@example.com", List(MMetadata("user_id", "123456")))),
          attachments = List(MAttachmetOrImage("text/plain", "myfile.txt", "ZXhhbXBsZSBmaWxl")),
          images = List(MAttachmetOrImage("image/png", "IMAGECID", "ZXhhbXBsZSBmaWxl")),
          google_analytics_campaign = Some("message.from_email@example.com"),
          google_analytics_domains = List("example.com"),
          merge_language = Some("handlebars")
        )
      )), defaultTimeout) {
        case MandrillSuccess(res) =>
          res.size shouldBe 1
          res.head.status shouldBe "sent"
          res.head.email shouldBe "recipient.email@example.com"
          res.head.reject_reason shouldBe Some("hard-bounce")
          res.head._id shouldBe "abc123abc123abc123abc123abc123"
        case x@_ => fail(s"Unsuccessful call: Should be MandrillSuccess, got $x")
      }
    }
  }

  "SendTemplate" should "work getting a valid List[MSendResponse]" in {
    withClient("/messages/send-template.json") { ws =>
      val instance = new MandrillClient(ws, new APIKey())
      whenReady(instance.messagesSendTemplate(MSendTemplateMessage(
        template_name = "example template_name",
        template_content = List(MVars("example name", "example content")),
        async = false,
        ip_pool = Some("Main Pool"),
        message = new MSendMsg(
          html = "<p>Example HTML content</p>",
          text = "Example text content",
          subject = "example subject",
          from_email = "message.from_email@example.com",
          from_name = "Example Name",
          to = List(MTo(
            email = "recipient.email@example.com",
            name = Some("Recipient Name")
          )),
          headers = Some(List(MHeader("Reply-To", "message.reply@example.com"))),
          important = false,
          bcc_address = Some("message.bcc_address@example.com"),
          merge = true,
          global_merge_vars = List(MVars("merge1", "merge1 content")),
          merge_vars = List(MMergeVars("recipient.email@example.com", List(MVars("merge2", "merge2 content")))),
          tags = List("password-resets"),
          subaccount = Some("customer-123"),
          metadata = List(MMetadata("website", "www.example.com")),
          recipient_metadata = List(MRecipientMetadata("recipient.email@example.com", List(MMetadata("user_id", "123456")))),
          attachments = List(MAttachmetOrImage("text/plain", "myfile.txt", "ZXhhbXBsZSBmaWxl")),
          images = List(MAttachmetOrImage("image/png", "IMAGECID", "ZXhhbXBsZSBmaWxl")),
          google_analytics_campaign = Some("message.from_email@example.com"),
          google_analytics_domains = List("example.com"),
          merge_language = Some("handlebars")
        )
      )), defaultTimeout) {
        case MandrillSuccess(res) =>
          res.size shouldBe 1
          res.head.status shouldBe "sent"
          res.head.email shouldBe "recipient.email@example.com"
          res.head.reject_reason shouldBe Some("hard-bounce")
          res.head._id shouldBe "abc123abc123abc123abc123abc123"
        case x@_ => fail(s"Unsuccessful call: Should be MandrillSuccess, got $x")
      }
    }
  }

  "Search" should "work getting a valid List[MSearchResponse]" in {
    withClient("/messages/search.json") { ws =>
      val instance = new MandrillClient(ws, new APIKey())
      whenReady(instance.messagesSearch(MSearch(
        query = "email:gmail.com",
        date_from = "2013-01-01",
        date_to = "2013-01-02",
        tags = List("password-reset", "welcome"),
        senders = List("sender@example.com"),
        api_keys = List("PmmzuovUZMPJsa73o3jjCw"),
        limit = 100
      )), defaultTimeout) { res =>
        res shouldBe MandrillSuccess(List(MSearchResponse(
          ts = 1365190000,
          _id = "abc123abc123abc123abc123",
          sender = "sender@example.com",
          template = Some("example-template"),
          subject = "example subject",
          email = "recipient.email@example.com",
          tags = List(
          "password-reset"
          ),
          opens = 42,
          opens_detail = List(MOpenDetail(
            ts = 1365190001,
            ip = "55.55.55.55",
            location = "Georgia, US",
            ua = "Linux/Ubuntu/Chrome/Chrome 28.0.1500.53"
          )),
          clicks = 42,
          clicks_detail = List(MClickDetails(
            ts = 1365190001,
            url = "http://www.example.com",
            ip = "55.55.55.55",
            location = "Georgia, US",
            ua = "Linux/Ubuntu/Chrome/Chrome 28.0.1500.53"
          )),
          state = "sent"
        )))
      }
    }
  }

  ignore should "work getting a valid List[MSearchResponse] with a TimeSeries" in {
    withClient("/messages/search-time-series.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.messagesSearchTimeSeries(MSearchTimeSeries(
        query = "email:gmail.com",
        date_from = "2013-01-01",
        date_to = "2013-01-02",
        tags = List(
          "password-reset",
          "welcome"
        ),
        senders = List(
          "sender@example.com"
        )
      )), defaultTimeout)(_ shouldBe MandrillSuccess(List(MTimeSeriesResponse(
        time = "2013-01-01 15:00:00",
        sent = 42,
        hard_bounces = 42,
        soft_bounces = 42,
        rejects = 42,
        complaints = 42,
        unsubs = 42,
        opens = 42,
        unique_opens = 42,
        clicks = 42,
        unique_clicks = 42
      ))))
    }
  }

//  "MessageInfo" should "work getting a valid MMessageInfoResponse" ignore {
//    val res = Await.result(client.messagesInfo(MMessageInfo(id = idOfMailForInfoTest)), DefaultConfig.defaultTimeout)
//    res.getClass shouldBe classOf[MMessageInfoResponse]
//    res._id shouldBe idOfMailForInfoTest
//    res.subject shouldBe "subject test"
//    res.email shouldBe "test@example.com"
//  }

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
//
//  "Parse" should "work getting a valid MParseResponse" in {
//    val res = Await.result(client.messagesParse(MParse(raw_message = """From: sender@example.com""")), DefaultConfig.defaultTimeout)
//    res.getClass shouldBe classOf[MParseResponse]
//    res.from_email shouldBe Some("sender@example.com")
//  }
//
//  "SendRaw" should "work getting a valid MParseResponse" in {
//    val res = Await.result(client.messagesSendRaw(validRawMessage), DefaultConfig.defaultTimeout)
//    res shouldBe Nil
//  }
//
//  "ListSchedule" should "work getting a valid List[MScheduleResponse]" in {
//    val res = Await.result(client.messagesListSchedule(MListSchedule(to = "test@recipient.com")), DefaultConfig.defaultTimeout)
//    res shouldBe Nil
//  }
}
