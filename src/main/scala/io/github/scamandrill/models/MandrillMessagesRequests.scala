package io.github.scamandrill.models

import play.api.libs.json._


/**
  * a single embedded image or attachment
  *
  * @param `type`  - the MIME type of the image
  * @param name    - the Content ID of the image - use <img src="cid:THIS_VALUE"> to reference the image in your HTML content
  * @param content - the content of the image as a base64-encoded string
  */
case class MAttachmetOrImage(`type`: String, name: String, content: String)
case object MAttachmetOrImage {
  implicit val writes = Json.writes[MAttachmetOrImage]
  implicit val reads = Json.reads[MAttachmetOrImage]
}

/**
  * A single merge variable
  *
  * @param name    - the merge variable's name. Merge variable names are case-insensitive and may not start with _
  * @param content - the merge variable's content
  */
case class MVars(name: String, content: JsValue)
case object MVars extends ((String, JsValue) => MVars) {
  implicit val writes = Json.writes[MVars]

  def apply[T](name: String, content: T)(implicit cw: Writes[T]): MVars = new MVars(name, Json.toJson(content))
}

/**
  * Per-recipient merge variables, which override global merge variables with the same name.
  *
  * @param rcpt - the email address of the recipient that the merge variables should apply to
  * @param vars - the recipient's merge variables
  */
case class MMergeVars(rcpt: String, vars: List[MVars])
case object MMergeVars {
  implicit val writes = Json.writes[MMergeVars]
}

/**
  * A single recipient's information.
  *
  * @param email  - the email address of the recipient
  * @param name   - the optional display name to use for the recipient
  * @param `type` - the header type to use for the recipient, defaults to "to" if not provided
  */
case class MTo(email: String, name: Option[String] = None, `type`: String = "to")
case object MTo {
  implicit val writes = Json.writes[MTo]
}

case class MHeaders(underlying: Map[String, String]) extends Optional[MHeaders]
case object MHeaders extends (Map[String,String] => MHeaders) {
  implicit val writes = new Writes[MHeaders] {
    override def writes(o: MHeaders): JsValue = Json.toJson(o.underlying)
  }
  def apply(entries: (String, String)*): MHeaders = MHeaders(Map(entries:_*))
  def apply(entries: TraversableOnce[MHeader]): MHeaders = MHeaders(entries.map(e => (e.name, e.value)).toSeq:_*)
}
/**
  * An Header
  *
  * @param name  - the key the header
  * @param value - the value of the header
  */
case class MHeader(name: String, value: String)
case object MHeader {
  implicit val writes = Json.writes[MHeader]
}

case class MMetadataEntry(key: String, value: JsScalar)

/**
  * An object containing metadata which will be represented as a JSON Object
  *
  * @param entries - the metadata entries
  */
case class MMetadata(entries: MMetadataEntry*) extends Optional[MMetadata] {}
case object MMetadata {
  implicit val writes = new Writes[MMetadata] {
    override def writes(o: MMetadata): JsValue = Json.toJson(Map(o.entries.map(e => (e.key, e.value)):_*))
  }
}

/**
  * Metadata for a single recipient
  *
  * @param rcpt   - the email address of the recipient that the metadata is associated with
  * @param values - an associated array containing the recipient's unique metadata. If a key exists in both the per-recipient metadata and the global metadata, the per-recipient metadata will be used.
  */
case class MRecipientMetadata(rcpt: String, values: MMetadata)
case object MRecipientMetadata {
  implicit val writes = Json.writes[MRecipientMetadata]
}

// TODO: Metadata should be "key" -> JsScalarValue
/**
  * The message to be sent
  *
  * @param html                      - the full HTML content to be sent
  * @param text                      - optional full text content to be sent
  * @param subject                   - the message subject
  * @param from_email                - the sender email address.
  * @param from_name                 - optional from name to be used
  * @param to                        - list of recipient information
  * @param headers                   - optional extra headers to add to the message (most headers are allowed)
  * @param important                 - whether or not this message is important, and should be delivered ahead of non-important messages
  * @param track_opens               - whether or not to turn on open tracking for the message
  * @param track_clicks              - whether or not to turn on click tracking for the message
  * @param auto_text                 - whether or not to automatically generate a text part for messages that are not given text
  * @param auto_html                 - whether or not to automatically generate an HTML part for messages that are not given HTML
  * @param inline_css                - whether or not to automatically inline all CSS styles provided in the message HTML - only for HTML documents less than 256KB in size
  * @param url_strip_qs              - whether or not to strip the query string from URLs when aggregating tracked URL data
  * @param preserve_recipients       - whether or not to expose all recipients in to "To" header for each email
  * @param view_content_link         - set to false to remove content logging for sensitive emails
  * @param bcc_address               - an optional address to receive an exact copy of each recipient's email
  * @param tracking_domain           - a custom domain to use for tracking opens and clicks instead of mandrillapp.com
  * @param signing_domain            - a custom domain to use for SPF/DKIM signing instead of mandrill (for "via" or "on behalf of" in email clients)
  * @param return_path_domain        - a custom domain to use for the messages's return-path
  * @param merge                     - whether to evaluate merge tags in the message. Will automatically be set to true if either merge_vars or global_merge_vars are provided.
  * @param global_merge_vars         - global merge variables to use for all recipients. You can override these per recipient.
  * @param merge_vars                - per-recipient merge variables, which override global merge variables with the same name.
  * @param tags                      - an array of string to key the message with. Stats are accumulated using tags, though we only store the first 100 we see, so this should not be unique or change frequently. Tags should be 50 characters or less. Any tags starting with an underscore are reserved for internal use and will cause errors.
  * @param subaccount                - the unique id of a subaccount for this message - must already exist or will fail with an error
  * @param google_analytics_domains  - an array of strings indicating for which any matching URLs will automatically have Google Analytics parameters appended to their query string automatically.
  * @param google_analytics_campaign - optional string indicating the value to set for the utm_campaign tracking parameter. If this isn't provided the email's from address will be used instead.
  * @param attachments               - an array of supported attachments to add to the message
  * @param images                    - an array of embedded images to add to the message
  */
class MSendMsg(val html: String,
               val text: String,
               val subject: String,
               val from_email: String,
               val from_name: String,
               val to: List[MTo],
               val headers: Option[MHeaders] = None,
               val important: Boolean = false,
               val track_opens: Boolean = false,
               val track_clicks: Boolean = false,
               val auto_text: Boolean = false,
               val auto_html: Boolean = false,
               val inline_css: Boolean = false,
               val url_strip_qs: Boolean = false,
               val preserve_recipients: Boolean = false,
               val view_content_link: Boolean = false,
               val bcc_address: Option[String] = None,
               val tracking_domain: Option[String] = None,
               val signing_domain: Option[String] = None,
               val return_path_domain: Option[String] = None,
               val merge: Boolean = false,
               val merge_language: Option[String] = None,
               val global_merge_vars: List[MVars] = List.empty,
               val merge_vars: List[MMergeVars] = List.empty,
               val tags: List[String] = List.empty,
               val subaccount: Option[String] = None,
               val google_analytics_domains: List[String] = List.empty,
               val google_analytics_campaign: Option[String] = None,
               val metadata: Option[MMetadata] = None,
               val recipient_metadata: List[MRecipientMetadata] = List.empty,
               val attachments: List[MAttachmetOrImage] = List.empty,
               val images: List[MAttachmetOrImage] = List.empty) {

  def copy(html: String = this.html,
           text: String = this.text,
           subject: String = this.subject,
           from_email: String = this.from_email,
           from_name: String = this.from_name,
           to: List[MTo] = this.to,
           headers: Option[MHeaders] = this.headers,
           important: Boolean = this.important,
           track_opens: Boolean = this.track_opens,
           track_clicks: Boolean = this.track_clicks,
           auto_text: Boolean = this.auto_text,
           auto_html: Boolean = this.auto_html,
           inline_css: Boolean = this.inline_css,
           url_strip_qs: Boolean = this.url_strip_qs,
           preserve_recipients: Boolean = this.preserve_recipients,
           view_content_link: Boolean = this.view_content_link,
           bcc_address: Option[String] = this.bcc_address,
           tracking_domain: Option[String] = this.tracking_domain,
           signing_domain: Option[String] = this.signing_domain,
           return_path_domain: Option[String] = this.return_path_domain,
           merge: Boolean = this.merge,
           merge_language: Option[String] = this.merge_language,
           global_merge_vars: List[MVars] = this.global_merge_vars,
           merge_vars: List[MMergeVars] = this.merge_vars,
           tags: List[String] = this.tags,
           subaccount: Option[String] = this.subaccount,
           google_analytics_domains: List[String] = this.google_analytics_domains,
           google_analytics_campaign: Option[String] = this.google_analytics_campaign,
           metadata: Option[MMetadata] = this.metadata,
           recipient_metadata: List[MRecipientMetadata] = this.recipient_metadata,
           attachments: List[MAttachmetOrImage] = this.attachments,
           images: List[MAttachmetOrImage] = this.images): MSendMsg = {

    new MSendMsg(html,
      text,
      subject,
      from_email,
      from_name,
      to,
      headers,
      important,
      track_opens,
      track_clicks,
      auto_text,
      auto_html,
      inline_css,
      url_strip_qs,
      preserve_recipients,
      view_content_link,
      bcc_address,
      tracking_domain,
      signing_domain,
      return_path_domain,
      merge: Boolean,
      merge_language,
      global_merge_vars,
      merge_vars,
      tags,
      subaccount,
      google_analytics_domains,
      google_analytics_campaign,
      metadata,
      recipient_metadata,
      attachments,
      images)
  }

  override def equals(other: Any) = other match {
    case o: MSendMsg =>
      o.html == this.html &&
        o.html == this.html &&
        o.text == this.text &&
        o.subject == this.subject &&
        o.from_email == this.from_email &&
        o.from_name == this.from_name &&
        o.to == this.to &&
        o.headers == this.headers &&
        o.important == this.important &&
        o.track_opens == this.track_opens &&
        o.track_clicks == this.track_clicks &&
        o.auto_text == this.auto_text &&
        o.inline_css == this.inline_css &&
        o.url_strip_qs == this.url_strip_qs &&
        o.preserve_recipients == this.preserve_recipients &&
        o.view_content_link == this.view_content_link &&
        o.bcc_address == this.bcc_address &&
        o.tracking_domain == this.tracking_domain &&
        o.signing_domain == this.signing_domain &&
        o.return_path_domain == this.return_path_domain &&
        o.merge == this.merge &&
        o.global_merge_vars == this.global_merge_vars &&
        o.merge_vars == this.merge_vars &&
        o.tags == this.tags &&
        o.subaccount == this.subaccount &&
        o.google_analytics_domains == this.google_analytics_domains &&
        o.google_analytics_campaign == this.google_analytics_campaign &&
        o.metadata == this.metadata &&
        o.recipient_metadata == this.recipient_metadata &&
        o.attachments == this.attachments &&
        o.images == this.images &&
        o.merge_language == this.merge_language
    case _ => false
  }
}
object MSendMsg {
  implicit val writes = new Writes[MSendMsg] {
    override def writes(msg: MSendMsg): JsValue = Json.obj(
      "html" -> msg.html,
      "text" -> msg.text,
      "subject" -> msg.subject,
      "from_email" -> msg.from_email,
      "from_name" -> msg.from_name,
      "to" -> msg.to,
      "headers" -> msg.headers,
      "important" -> msg.important,
      "track_opens" -> msg.track_opens,
      "track_clicks" -> msg.track_clicks,
      "auto_text" -> msg.auto_text,
      "auto_html" -> msg.auto_html,
      "inline_css" -> msg.inline_css,
      "url_strip_qs" -> msg.url_strip_qs,
      "preserve_recipients" -> msg.preserve_recipients,
      "view_content_link" -> msg.view_content_link,
      "bcc_address" -> msg.bcc_address,
      "tracking_domain" -> msg.tracking_domain,
      "signing_domain" -> msg.signing_domain,
      "return_path_domain" -> msg.return_path_domain,
      "merge" -> msg.merge,
      "global_merge_vars" -> msg.global_merge_vars,
      "merge_vars" -> msg.merge_vars,
      "tags" -> msg.tags,
      "subaccount" -> msg.subaccount,
      "google_analytics_domains" -> msg.google_analytics_domains,
      "google_analytics_campaign" -> msg.google_analytics_campaign,
      "metadata" -> msg.metadata,
      "recipient_metadata" -> msg.recipient_metadata,
      "attachments" -> msg.attachments,
      "images" -> msg.images,
      "merge_language" -> msg.merge_language
    )
  }
}


/**
  * A message to be sent
  *
  * @param message - the information on the message to send
  * @param async   - enable a background sending mode that is optimized for bulk sending. In async mode, messages/send will immediately return a status of "queued" for every recipient. To handle rejections when sending in async mode, set up a key for the 'add' event. Defaults to false for messages with no more than 10 recipients; messages with more than 10 recipients are always sent asynchronously, regardless of the value of async.
  * @param ip_pool - the name of the dedicated ip pool that should be used to send the message. If you do not have any dedicated IPs, this parameter has no effect. If you specify a pool that does not exist, your default pool will be used instead.
  * @param send_at - when this message should be sent as a UTC timestamp in YYYY-MM-DD HH:MM:SS format. If you specify a time in the past, the message will be sent immediately. An additional fee applies for scheduled email, and this feature is only available to accounts with a positive balance.
  */
case class MSendMessage(message: MSendMsg,
                        async: Boolean = false,
                        ip_pool: Option[String] = None,
                        send_at: Option[String] = None)

case object MSendMessage {
  implicit val writes = Json.writes[MSendMessage]
}

/**
  * A message to be sent through a template
  *
  * @param template_name    - the immutable name or slug of a template that exists in the user's account. For backwards-compatibility, the template name may also be used but the immutable slug is preferred.
  * @param template_content - an array of template content to send. Each item in the array should be a struct with two keys - name: the name of the content block to set the content for, and content: the actual content to put into the block
  * @param message          - the information on the message to send
  * @param async            - enable a background sending mode that is optimized for bulk sending. In async mode, messages/send will immediately return a status of "queued" for every recipient. To handle rejections when sending in async mode, set up a key for the 'add' event. Defaults to false for messages with no more than 10 recipients; messages with more than 10 recipients are always sent asynchronously, regardless of the value of async.
  * @param ip_pool          - the name of the dedicated ip pool that should be used to send the message. If you do not have any dedicated IPs, this parameter has no effect. If you specify a pool that does not exist, your default pool will be used instead.
  * @param send_at          - when this message should be sent as a UTC timestamp in YYYY-MM-DD HH:MM:SS format. If you specify a time in the past, the message will be sent immediately. An additional fee applies for scheduled email, and this feature is only available to accounts with a positive balance.
  */
case class MSendTemplateMessage(template_name: String,
                                template_content: List[MVars],
                                message: MSendMsg,
                                async: Boolean = false,
                                ip_pool: Option[String] = None,
                                send_at: Option[String] = None)
case object MSendTemplateMessage {
  implicit val writes = Json.writes[MSendTemplateMessage]
}

/**
  * The information about the search
  *
  * @param query     - the search terms to find matching messages for
  * @param date_from - start date
  * @param date_to   - end date
  * @param tags      - an array of key names to narrow the search to, will return messages that contain ANY of the tags
  * @param senders   - an array of sender addresses to narrow the search to, will return messages sent by ANY of the senders
  * @param api_keys  - an array of API keys to narrow the search to, will return messages sent by ANY of the keys
  * @param limit     - the maximum number of results to return, defaults to 100, 1000 is the maximum
  */
case class MSearch(query: String,
                   date_from: String,
                   date_to: String,
                   tags: List[String] = List.empty,
                   senders: List[String] = List.empty,
                   api_keys: List[String] = List.empty,
                   limit: Int = 100)
case object MSearch {
  implicit val writes = Json.writes[MSearch]
}

/**
  * The information about the search
  *
  * @param query     - the search terms to find matching messages for
  * @param date_from - start date
  * @param date_to   - end date
  * @param tags      - an array of key names to narrow the search to, will return messages that contain ANY of the tags
  * @param senders   - an array of sender addresses to narrow the search to, will return messages sent by ANY of the senders
  */
case class MSearchTimeSeries(query: String,
                             date_from: String,
                             date_to: String,
                             tags: List[String] = List.empty,
                             senders: List[String] = List.empty)
case object MSearchTimeSeries {
  implicit val writes = Json.writes[MSearchTimeSeries]
}

/**
  * Message information
  *
  * @param id  - the unique id of the message to get - passed as the "_id" field in webhooks, send calls, or search calls
  */
case class MMessageInfo(id: String)
case object MMessageInfo {
  implicit val writes = Json.writes[MMessageInfo]
}

/**
  * The raw message to parse
  *
  * @param raw_message - the raw message
  */
case class MParse(raw_message: String)
case object MParse {
  implicit val writes = Json.writes[MParse]
}

/**
  * The raw message to send
  *
  * @param raw_message        - the raw message
  * @param from_email         - optionally define the sender address - otherwise we'll use the address found in the provided headers
  * @param from_name          - optionally define the sender alias
  * @param to                 - optionally define the recipients to receive the message - otherwise we'll use the To, Cc, and Bcc headers provided in the document
  * @param async              - enable a background sending mode that is optimized for bulk sending. In async mode, messages/sendRaw will immediately return a status of "queued" for every recipient. To handle rejections when sending in async mode, set up a key for the 'add' event. Defaults to false for messages with no more than 10 recipients; messages with more than 10 recipients are always sent asynchronously, regardless of the value of async.
  * @param ip_pool            - the name of the dedicated ip pool that should be used to send the message. If you do not have any dedicated IPs, this parameter has no effect. If you specify a pool that does not exist, your default pool will be used instead.
  * @param send_at            - when this message should be sent as a UTC timestamp in YYYY-MM-DD HH:MM:SS format. If you specify a time in the past, the message will be sent immediately.
  * @param return_path_domain - a custom domain to use for the messages's return-path
  */
case class MSendRaw(raw_message: String,
                    from_email: Option[String] = None,
                    from_name: Option[String] = None,
                    to: List[String] = List.empty,
                    async: Boolean = false,
                    ip_pool: Option[String] = None,
                    send_at: Option[String] = None,
                    return_path_domain: Option[String] = None)
case object MSendRaw {
  implicit val writes = Json.writes[MSendRaw]
}

/**
  * Parameter to list the scheduled mails
  *
  * @param to  - an optional recipient address to restrict results to
  */
case class MListSchedule(to: String)
case object MListSchedule {
  implicit val writes = Json.writes[MListSchedule]
}

/**
  * Info about the mail to cancel the schedule
  *
  * @param id  - a scheduled email id, as returned by any of the messages/send calls or messages/list-scheduled
  */
case class MCancelSchedule(id: String)
case object MCancelSchedule {
  implicit val writes = Json.writes[MCancelSchedule]
}

/**
  * Info about the mail to cancel the schedule
  *
  * @param id      - a scheduled email id, as returned by any of the messages/send calls or messages/list-scheduled
  * @param send_at - the new UTC timestamp when the message should sent. Mandrill can't time travel, so if you specify a time in past the message will be sent immediately
  */
case class MReSchedule(id: String, send_at: String)
case object MReSchedule {
  implicit val writes = Json.writes[MReSchedule]
}
