package io.github.scamandrill

import io.github.scamandrill.models._
import io.github.scamandrill.client.{MandrillAsyncClient, MandrillError, MandrillResponseException, UnsuccessfulResponseException}
import org.scalatest.Matchers
import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import io.github.scamandrill.models.MSearch
import scala.util.Success
import io.github.scamandrill.models.MSearchTimeSeries
import io.github.scamandrill.models.MTo
import scala.util.Failure
import scala.concurrent.duration._

object MandrillTestUtils extends Matchers {

  val validRoute = MInboundRoute(
    domain= "example.com",
    pattern= "mailbox-*",
    url= "http://example.com"
  )

  val validSubaccount = MSubaccount(
    id = "testingsubaccount",
    name = "testingsubaccount",
    notes = "subaccount test",
    custom_quota = 250
  )

  val validSubaccount2 = MSubaccount(
    id = "testingsubaccount2",
    name = "testingsubaccount2",
    notes = "subaccount test",
    custom_quota = 250
  )

  val validWebhook = MWebhook(
    url = "http://example/key-key",
    description = "My Example Webhook",
    events = List("send", "open", "click")
  )

  val validWebhookUpdate = MWebhookUpdate(
    url = "http://example/key-key",
    description = "My Example Webhook",
    id = 4,
    events = List("send", "open", "click")
  )


  val idOfMailForInfoTest = "3edaed120eb144debb843893f4d92179"

  val validRawMessage = MSendRaw(raw_message =  """From: sender@example.com""")

  val validNonPublidhedTemplate = MTemplate(
    name = "templatetest",
    from_email = "from_email@example.com",
    from_name = "Example Name",
    subject = "example subject",
    code = "<div>example code</div>",
    text = "Example text content",
    publish = false,
    labels = List("templatetest")
  )

  val validTemplateRender = MTemplateRender(
    template_name = "templatetest",
    template_content = List(MTemplateCnt(name = "editable" , content = "<div>content to inject *|MERGE1|*</div>")),
    merge_vars = List(MTemplateCnt(name = "merge1" , content = "merge1 content"))
  )


  val validNonPublidhedTemplate2 = MTemplate(
    name = "templatetest2",
    from_email = "from_email@example.com",
    from_name = "Example Name",
    subject = "example subject",
    code = "<div>example code</div>",
    text = "Example text content",
    publish = false,
    labels = List("templatetest2")
  )

  val validMessage = new MSendMsg(
    html = "<h1>test</h1>",
    text = "test",
    subject = "subject test",
    from_email = "scamandrill@test.com",
    from_name = "Scamandrill",
    to = List(MTo("test@recipient.com")),
    bcc_address = Some("somebcc@address.com"),
    tracking_domain = Some("fromname"),
    signing_domain = Some("fromname"),
    return_path_domain = Some("fromname")
  )

  val validSearch = MSearch(
    query = "email:gmail.com",
    date_from = "2016-01-01",
    date_to = "2016-01-02")

  val validSearchTimeSeries = MSearchTimeSeries(
    query = "email:gmail.com",
    date_from = "2016-01-01",
    date_to = "2016-01-02")
}
