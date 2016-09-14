---
layout: nothome
active: migration
title: Migration
---

## scamandrill 2.0 ⇒ 2.4/2.5

If you have been using scamandrill 2.0, there are a few changes that you are going to have to make in order to use scamandrill 2.4/2.5.

#### API key is set on the client rather than on the request
In scamandrill 2.0, each of the client API calls took either the API key or a model with an API key property.

~~~ scala
val client = new MandrillClient()

client.usersInfo(MKey(key = "YOURAPIKEY"))
~~~

Scamandrill 2.4/2.5 has moved the API key specification to the client rather than each call.

~~~ scala
val client = Scamandrill().getClient(key = "YOURAPIKEY")

client.usersInfo()
~~~

#### Client API calls return a `Future[Try[_]]` response rather than a `Future[_]`

You were likely doing something like this in scamandrill 2.0

~~~ scala
val client = new MandrillClient()

client.usersPing(MKey(key = "YOURAPIKEY")).onComplete {
  case Success(pong) => logger.info(s"Got ${pong.PONG} from the server")
  case Failure(ex) => logger.error("Unable to ping the mandrill API", ex)
}
~~~


In scamandrill 2.4/2.5 you will probably do it this way

~~~ scala
val client = new MandrillClient()

client.usersPing().map {
  case Success(pong) => logger.info(s"Got ${pong.PONG} from the server")
  case Failure(ex) => logger.error("Unable to ping the mandrill API", ex)
}
~~~



#### Models have changed to reflect the API
Some of the models have changed to more accurately reflect the API. The model most affected is the MSendMsg.
In order to make the transition to the new objects easier, we've implemented a set of implicits to ease the conversion to the new shapes.
Here's an example using the implicits:

~~~ scala
import io.github.scamandrill.client.implicits._

val message = MSendMessage(
  async = false,
  // ip_pool is an optional parameter and .? lifts the String to an Option[String]
  ip_pool = "Main Pool".?, 
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
    // MVars was previously MVars(name: String, value: String), now it's MVars(name: String, value: JsValue)
    // but MVars has an alternate apply method that takes (String, String), so usage can be the same.
    global_merge_vars = List(MVars("merge1", "merge1 content")),
    merge_vars = List(MMergeVars("recipient.email@example.com", List(MVars("merge2", "merge2 content")))),
    tags = List("password-resets"),
    subaccount = "customer-123".?,
    // metadata was a List of MMetadata(name: String, value: String), but the API allows for any scalar Json value (boolean, string, number)
    metadata = MMetadata("website" -> "www.example.com").?,
    recipient_metadata = List(
      MRecipientMetadata("recipient.email@example.com", MMetadata("user_id" -> 123456))
    ),
    attachments = List(MAttachmetOrImage("text/plain", "myfile.txt", "ZXhhbXBsZSBmaWxl")),
    images = List(MAttachmetOrImage("image/png", "IMAGECID", "ZXhhbXBsZSBmaWxl")),
    google_analytics_campaign = "message.from_email@example.com".?,
    google_analytics_domains = List("example.com"),
    // This can also be ommitted in favor of your sender defaults since it has a default value of None
    merge_language = "handlebars".?
  )
)
~~~
