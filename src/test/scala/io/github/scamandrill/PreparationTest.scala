package io.github.scamandrill

import io.github.scamandrill.models._
import scala.util.{Failure, Success}
import io.github.scamandrill.MandrillTestUtils._
import io.github.scamandrill.models.MSendResponse
import io.github.scamandrill.models.MTemplateAddResponses
import io.github.scamandrill.models.MTemplate
import io.github.scamandrill.models.MTo
import io.github.scamandrill.models.MSendMessage


class PreparationTest extends MandrillSpec {

  val realKey = "REPLACEME" //ADD YOUR REAL (NON TESTING) KEY HERE

  ignore should "create the testing template" in {
    mandrillBlockingClient.templateAdd(MTemplate(
      key = realKey,
      name = "testtemplate",
      from_email = "from_email@example.com",
      from_name = "Example Name",
      subject = "example subject",
      code = "<div>example code</div>",
      text = "Example text content",
      publish = false,
      labels = List("test")
    ))
    match {
      case Success(res) =>
        res.getClass shouldBe classOf[MTemplateAddResponses]
        println(res)
      case Failure(ex) => fail(ex)
    }
  }

  ignore should "send a test email with a not test key (a real message)" in {
    val msg = new MSendMsg(
      html = "<h1>test</h1>",
      text = "test",
      subject = "subject test",
      from_email = "scamandrill@test.com",
      from_name = "Scamandrill",
      to = List(MTo("test@example.com")),
      bcc_address = Some(""),
      tracking_domain = Some("fromname"),
      signing_domain = Some("fromname"),
      return_path_domain = Some("fromname"),
      tags = List("exampletag1", "exampletag2")
    )
    mandrillBlockingClient.messagesSend(MSendMessage(message = msg, key = realKey)) match {
      case Success(res) =>
        println("your id of the mail just sent is the following: " + res.head._id +
          " you should set the value of MandrillTestUtils.idOfMailForInfoTest to this value")
        res.head.getClass shouldBe classOf[MSendResponse]
      case Failure(ex) => fail(ex)
    }
  }

}