package io.github.scamandrill.client

import java.text.SimpleDateFormat
import java.time.LocalDateTime

import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.client.implicits._
import io.github.scamandrill.models._
import org.joda.time.DateTime
import org.scalatest.Matchers
import org.scalatest.Matchers._

import scala.concurrent.duration._
import play.api.libs.json.JsString

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success}

class MessageCallsTest extends MandrillSpec {

  "Send" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=send" in {
    withMockClient("/messages/send.json") { ws =>
      val instance = new MandrillClient(ws)
      whenReady(instance.messagesSend(MSendMessage(
        async = false,
        ip_pool = "Main Pool".?,
        message = new MSendMsg(
          html = "<p>Example HTML content</p>",
          text = "Example text content",
          subject = "example subject",
          from_email = "message.from_email@example.com",
          from_name = "Example Name",
          to = List(MTo(
            email = "recipient.email@example.com",
            name = "Recipient Name".?
          )),
          headers = MHeaders("Reply-To" -> "message.reply@example.com").?,
          important = false,
          bcc_address = "message.bcc_address@example.com".?,
          merge = true,
          global_merge_vars = List(MVars("merge1", JsString("merge1 content"))),
          merge_vars = List(MMergeVars("recipient.email@example.com", List(MVars("merge2", "merge2 content")))),
          tags = List("password-resets"),
          subaccount = "customer-123".?,
          metadata = MMetadata("website" -> "www.example.com").?,
          recipient_metadata = List(
            MRecipientMetadata("recipient.email@example.com", MMetadata("user_id" -> 123456))
          ),
          attachments = List(MAttachmetOrImage("text/plain", "myfile.txt", "ZXhhbXBsZSBmaWxl")),
          images = List(MAttachmetOrImage("image/png", "IMAGECID", "ZXhhbXBsZSBmaWxl")),
          google_analytics_campaign = "message.from_email@example.com".?,
          google_analytics_domains = List("example.com"),
          merge_language = "handlebars".?
        )
      )), defaultTimeout) {
        case Success(res) =>
          res.size shouldBe 1
          res.head.status shouldBe "sent"
          res.head.email shouldBe "recipient.email@example.com"
          res.head.reject_reason shouldBe "hard-bounce".?
          res.head._id shouldBe "abc123abc123abc123abc123abc123"
        case x@_ => fail(s"Unsuccessful call: Should be Success, got $x")
      }
    }
  }

  "SendTemplate" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=send-template" in {
    withMockClient("/messages/send-template.json") { ws =>
      val instance = new MandrillClient(ws)
      whenReady(instance.messagesSendTemplate(MSendTemplateMessage(
        template_name = "example template_name",
        template_content = List(MVars("example name", JsString("example content"))),
        async = false,
        ip_pool = "Main Pool".?,
        message = new MSendMsg(
          html = "<p>Example HTML content</p>",
          text = "Example text content",
          subject = "example subject",
          from_email = "message.from_email@example.com",
          from_name = "Example Name",
          to = List(MTo(
            email = "recipient.email@example.com",
            name = "Recipient Name".?
          )),
          headers = MHeaders("Reply-To" -> "message.reply@example.com").?,
          important = false,
          bcc_address = "message.bcc_address@example.com".?,
          merge = true,
          global_merge_vars = List(MVars("merge1", JsString("merge1 content"))),
          merge_vars = List(MMergeVars("recipient.email@example.com", List(MVars("merge2", JsString("merge2 content"))))),
          tags = List("password-resets"),
          subaccount = "customer-123".?,
          metadata = MMetadata("website" -> "www.example.com").?,
          recipient_metadata = List(MRecipientMetadata("recipient.email@example.com", MMetadata("user_id" -> 123456))),
          attachments = List(MAttachmetOrImage("text/plain", "myfile.txt", "ZXhhbXBsZSBmaWxl")),
          images = List(MAttachmetOrImage("image/png", "IMAGECID", "ZXhhbXBsZSBmaWxl")),
          google_analytics_campaign = "message.from_email@example.com".?,
          google_analytics_domains = List("example.com"),
          merge_language = "handlebars".?
        )
      )), defaultTimeout) {
        case Success(res) =>
          res.size shouldBe 1
          res.head.status shouldBe "sent"
          res.head.email shouldBe "recipient.email@example.com"
          res.head.reject_reason shouldBe "hard-bounce".?
          res.head._id shouldBe "abc123abc123abc123abc123abc123"
        case x@_ => fail(s"Unsuccessful call: Should be Success, got $x")
      }
    }
  }

  "Search" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=search" in {
    withMockClient("/messages/search.json") { ws =>
      val instance = new MandrillClient(ws)
      whenReady(instance.messagesSearch(MSearch(
        query = "email:gmail.com".?,
        date_from = "2013-01-01".?,
        date_to = "2013-01-02".?,
        tags = List("password-reset", "welcome"),
        senders = List("sender@example.com"),
        api_keys = List("PmmzuovUZMPJsa73o3jjCw"),
        limit = 100
      )), defaultTimeout) { res =>
        res shouldBe Success(List(MSearchResponse(
          ts = 1365190000,
          _id = "abc123abc123abc123abc123",
          sender = "sender@example.com",
          template = "example-template".?,
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
          )).?,
          clicks = 42,
          clicks_detail = List(MClickDetails(
            ts = 1365190001,
            url = "http://www.example.com",
            ip = "55.55.55.55",
            location = "Georgia, US",
            ua = "Linux/Ubuntu/Chrome/Chrome 28.0.1500.53"
          )).?,
          state = "sent"
        )))
      }
    }
  }

  "searchTimeSeries" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=search-time-series" in {
    withMockClient("/messages/search-time-series.json"){ wc =>
      val instance = new MandrillClient(wc)
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
      )), defaultTimeout)(_ shouldBe Success(List(MTimeSeriesResponse(
        time = "2013-01-01 15:00:00",
        sent = 42,
        hard_bounces = 42,
        soft_bounces = 42,
        rejects = 42,
        complaints = 42,
        unsubs = Some(42),
        opens = 42,
        unique_opens = 42,
        clicks = 42,
        unique_clicks = 42
      ))))
    }
  }

  "MessageInfo" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=info" in {
    withMockClient("/messages/info.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.messagesInfo(MMessageInfo(
        id = "abc123abc123abc123abc123"
      )), defaultTimeout)(_ shouldBe Success(MMessageInfoResponse(
        ts = 1365190000,
        _id = "abc123abc123abc123abc123",
        sender = "sender@example.com",
        template = "example-template".?,
        subject = "example subject",
        email = "recipient.email@example.com",
        tags = List(
          "password-reset"
        ),
        opens = 42,
        opens_detail = List(
          MOpenDetail(
            ts = 1365190001,
            ip = "55.55.55.55",
            location = "Georgia, US",
            ua = "Linux/Ubuntu/Chrome/Chrome 28.0.1500.53"
          )
        ),
        clicks = 42,
        clicks_detail = List(
          MClickDetails(
            ts = 1365190001,
            url = "http://www.example.com",
            ip = "55.55.55.55",
            location = "Georgia, US",
            ua = "Linux/Ubuntu/Chrome/Chrome 28.0.1500.53"
          )
        ),
        state = "sent",
        smtp_events = List(
          MSmtpEvent(
            ts = 1365190001,
            `type` = "sent",
            diag = "250 OK"
          )
        )
      )))
    }
  }

  "Parse" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=parse" in {
    withMockClient("/messages/parse.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.messagesParse(MParse(
        raw_message = "From: sender@example.com\nTo: recipient.email@example.com\nSubject: Some Subject\n\nSome content."
      )), defaultTimeout)(_ shouldBe Success(MParseResponse(
        subject = "Some Subject".?,
        from_email = "sender@example.com".?,
        from_name = "Sender Name".?,
        to = Some(List(
          MToResponse(
            email = "recipient.email@example.com",
            name = "Recipient Name".?
          )
        )),
        text = "Some text content".?,
        html = "<p>Some HTML content</p>".?,
        attachments = Some(List(
          MAttachmetOrImage(
            name = "example.txt",
            `type`= "text/plain",
            content = "example non-binary content"
          )
        )),
        images = Some(List(
          MAttachmetOrImage(
            name = "IMAGEID",
            `type` = "image/png",
            content = "ZXhhbXBsZSBmaWxl"
          )
        ))
      )))
    }
  }

  "SendRaw" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=send-raw" in {
    withMockClient("/messages/send-raw.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.messagesSendRaw(MSendRaw(
        raw_message = "From: sender@example.com\nTo: recipient.email@example.com\nSubject: Some Subject\n\nSome content.",
        from_email = "sender@example.com".?,
        from_name = "From Name".?,
        to = List("recipient.email@example.com").?,
        async = false,
        ip_pool = "Main Pool".?,
        send_at = "example send_at".?,
        return_path_domain = "mail.com".?
      )), defaultTimeout)(_ shouldBe Success(List(MSendResponse(
        email = "recipient.email@example.com",
        status = "sent",
        reject_reason = "hard-bounce".?,
        _id = "abc123abc123abc123abc123"
      ))))
    }
  }

  "ListSchedule" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=list-scheduled" in {
    withMockClient("/messages/list-scheduled.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.messagesListSchedule(MListSchedule(
        to = "test.recipient@example.com"
      )), defaultTimeout)(_ shouldBe Success(List(MScheduleResponse(
        _id = "I_dtFt2ZNPW5QD9-FaDU1A",
        created_at = "2013-01-20 12:13:01",
        send_at = "2021-01-05 12:42:01",
        from_email = "sender@example.com",
        to = "test.recipient@example.com",
        subject = "This is a scheduled email"
      ))))
    }
  }

  "CancelScheduled" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=cancel-scheduled" in {
    withMockClient("/messages/cancel-scheduled.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.messagesCancelSchedule(MCancelSchedule(
        id = "I_dtFt2ZNPW5QD9-FaDU1A"
      )), defaultTimeout)(_ shouldBe Success(MScheduleResponse(
        _id = "I_dtFt2ZNPW5QD9-FaDU1A",
        created_at = "2013-01-20 12:13:01",
        send_at = "2021-01-05 12:42:01",
        from_email = "sender@example.com",
        to = "test.recipient@example.com",
        subject = "This is a scheduled email"
      )))
    }
  }

  "Reschedule" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=reschedule" in {
    withMockClient("/messages/reschedule.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.messagesReschedule(MReSchedule(
        id = "I_dtFt2ZNPW5QD9-FaDU1A",
        send_at = "20120-06-01 08:15:01"
      )), defaultTimeout)(_ shouldBe Success(MScheduleResponse(
        _id = "I_dtFt2ZNPW5QD9-FaDU1A",
        created_at = "2013-01-20 12:13:01",
        send_at = "2021-01-05 12:42:01",
        from_email = "sender@example.com",
        to = "test.recipient@example.com",
        subject = "This is a scheduled email"
      )))
    }
  }

  "Content" should "handle the example at https://mandrillapp.com/api/docs/messages.JSON.html#method=content" in {
    withMockClient("/messages/content.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.messagesContent(MMessageInfo(
        id = "abc123abc123abc123abc123"
      )), defaultTimeout)(_ shouldBe Success(MContentResponse(
        ts = 1365190000,
        _id = "abc123abc123abc123abc123",
        from_email = "sender@example.com",
        from_name = "Sender Name",
        subject = "example subject",
        to = MToResponse(
          email = "recipient.email@example.com",
          name = "Recipient Name".?
        ),
        tags = List(
          "password-reset"
        ),
        text = "Some text content",
        html = "<p>Some HTML content</p>".?,
        attachments = List(
          MAttachmetOrImage(
            name = "example.txt",
            `type` = "text/plain",
            content = "QSBzaW1wbGUgdGV4dCBzdHJpbmcgYXR0YWNobWVudA=="
          )
        )
      )))
    }
  }

  import io.github.scamandrill.MandrillTestUtils._
  Await.result({
    actualClient match {
      case Some(client) =>
        client.messagesSearch(MSearch(
          query = s"""subject:"${validMessage.subject}" AND state:sent""".?,
          date_to = DateTime.now.toString("yyyy-MM-dd").?,
          date_from = DateTime.now.minusDays(30).toString("yyyy-MM-dd").? // Mandrill keeps info for the last 30 days
        )).map { testResponse =>
          testResponse.toOption.flatMap(_.find(m => m.state == "sent")) match {
            case Some(testMessageResponse) =>
              "Actual message calls" should "send a valid message" taggedAs ActualAPICall in {
                whenReady(client.messagesSend(MSendMessage(message = validMessage)).map { res =>
                  res
                }, defaultTimeout) {
                  case Success(res) =>
                    res.headOption match {
                      case r@Some(mres) =>
                        Set("queued", "sent").contains(mres.status) shouldBe true
                        mres.email shouldBe "test@recipient.com"
                        mres.reject_reason shouldBe None
                      case None => fail("we should get a response when we send a message")
                    }
                  case Failure(t) => fail(t)
                }
              }

              it should "send a template message" taggedAs ActualAPICall in {
                whenReady(client.messagesSendTemplate(
                  MSendTemplateMessage(
                    template_name = "testtemplate",
                    template_content = List(MVars("first", "example")),
                    message = validMessage
                  )
                ), defaultTimeout) {
                  case Success(res) =>
                    res.headOption match {
                      case Some(mres) =>
                        Set("queued", "sent").contains(mres.status) shouldBe true
                        mres.email shouldBe "test@recipient.com"
                        mres.reject_reason shouldBe None
                      case None => fail("We should get at least one response")
                    }

                  case Failure(t) => fail(t)
                }
              }

              it should "not find anything if I search for an email we didn't send" taggedAs ActualAPICall in {
                whenReady(client.messagesSearch(validSearch), defaultTimeout)(_ shouldBe Success(Nil))
              }

              it should "not find any time series for an email we didn't send" taggedAs ActualAPICall in {
                whenReady(client.messagesSearchTimeSeries(validSearchTimeSeries), defaultTimeout)(_ shouldBe Success(Nil))
              }

              it should "load info about the message we sent" taggedAs ActualAPICall in {
                whenReady(client.messagesInfo(MMessageInfo(id = testMessageResponse._id)), defaultTimeout) {
                  case Success(res) =>
                    res._id shouldBe testMessageResponse._id
                    res.email shouldBe testMessageResponse.email
                    res.subject shouldBe testMessageResponse.subject
                  case Failure(t) => fail(t)
                }
              }

              it should "load the content of the message we sent" taggedAs ActualAPICall in {
                whenReady(client.messagesContent(MMessageInfo(id = testMessageResponse._id)), defaultTimeout) {
                  case Success(res) =>
                    res._id shouldBe testMessageResponse._id
                    res.subject shouldBe testMessageResponse.subject
                  case Failure(t) => fail(t)
                }
              }

              it should "parse raw content" taggedAs ActualAPICall in {
                whenReady(client.messagesParse(MParse(raw_message = validRawMessage.raw_message)), defaultTimeout) {
                  case Success(res) =>
                    res.from_email shouldBe Some(senderAddress)
                  case Failure(t) => fail(t)
                }
              }

              it should "send raw content" taggedAs ActualAPICall in {
                whenReady(client.messagesSendRaw(validRawMessage), defaultTimeout) {
                  case Success(res) => res.headOption match {
                    case Some(mres) => mres.email shouldBe "recipient.email@example.com"
                    case None => fail("we should get a response to the raw message we sent")
                  }
                  case Failure(t) => fail(t)
                }
              }

              it should "get an empty list of scheduled email (we didn't schedule mail to this guy, right?)" taggedAs ActualAPICall in {
                whenReady(client.messagesListSchedule(MListSchedule(to = "nobody@nowhere.com")), defaultTimeout)(_ shouldBe Success(Nil))
              }
            case None => "Actual message calls" should "find a test message to use for tests" taggedAs ActualAPICall in {
              client.messagesSend(MSendMessage(validMessage))
              fail("Unable to get a test message response for further message tests...sending a message so that it should succeed next time")
            }
          }
        }
      case None => Future.successful {
        "Actual message calls" should "be skipped if there is no actual client" taggedAs ActualAPICall in {
          assume(actualClient.isDefined)
        }
      }
    }
  }, 30.seconds)
}
