package io.github.scamandrill

import io.github.scamandrill.models._
import org.joda.time.{DateTime, DateTimeZone}
import org.joda.time.format.DateTimeFormat

object MandrillTestUtils {
  val utcDateTimeParser: String => DateTime =
    DateTime.parse(_, DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss").withZoneUTC())
    .toDateTime(DateTimeZone.getDefault)
  val dateParser: String => DateTime = DateTime.parse(_, DateTimeFormat.forPattern("yyyy-MM-dd"))

  val testTag = "exampletag1"

  val testTrackingDomain = "test.com"

  val whitelistAddress = "whitelist@example.com"

  val senderAddress = sys.env.getOrElse("SCAMANDRILL_DEFAULT_SENDER", "scamandrill@test.com")

  val validRoute = MInboundRoute(
    domain = "example.com",
    pattern = "mailbox-*",
    url = "http://example.com"
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

  val validRawMessage = MSendRaw(raw_message = s"""From: $senderAddress\nTo: recipient.email@example.com\nSubject: Some Subject\n\nSome content.""")

  val validNonPublishedTemplate = MTemplate(
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
    template_content = List(MTemplateCnt(name = "editable", content = "<div>content to inject *|MERGE1|*</div>")),
    merge_vars = List(MTemplateCnt(name = "merge1", content = "merge1 content"))
  )

  val validMessage = new MSendMsg(
    html = "<h1>test</h1>",
    text = "test",
    subject = "subject test",
    from_email = senderAddress,
    view_content_link = Some(true),
    from_name = "Scamandrill",
    to = List(MTo("test@recipient.com")),
    bcc_address = Some("somebcc@address.com"),
    tracking_domain = Some("fromname"),
    signing_domain = Some("fromname"),
    return_path_domain = Some("fromname"),
    tags = List(testTag)
  )

  val validSearch = MSearch(
    query = Some("email:gmail.com"),
    date_from = Some(dateParser("2016-01-01")),
    date_to = Some(dateParser("2016-01-02")))

  val validSearchTimeSeries = MSearchTimeSeries(
    query = "email:gmail.com",
    date_from = Some(dateParser("2013-01-01")),
    date_to = Some(dateParser("2013-01-02"))
  )
}