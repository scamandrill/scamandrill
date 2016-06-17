package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * Individual opens for the message
  *
  * @param ts       - the unix timestamp from when the message was opened
  * @param ip       - the IP address that generated the open
  * @param location - the approximate region and country that the opening IP is located
  * @param ua       - the email client or browser data of the open
  */
case class MOpenDetail(ts: Int, ip: String, location: String, ua: String)
case object MOpenDetail {
  implicit val reads = Json.reads[MOpenDetail]
}

/**
  * Individual clicks for the message
  *
  * @param ts       - the unix timestamp from when the message was opened
  * @param ip       - the IP address that generated the open
  * @param location - the approximate region and country that the opening IP is located
  * @param ua       - the email client or browser data of the open
  * @param url      - the email client or browser data of the click
  */
case class MClickDetails(ts: Int, ip: String, location: String, ua: String, url: String)
case object MClickDetails {
  implicit val reads = Json.reads[MClickDetails]
}

//TODO: metadata
/**
  * The information for a single matching message
  *
  * @param ts            - the Unix timestamp from when this message was sent
  * @param _id           - the message's unique id
  * @param sender        - the email address of the sender
  * @param template      - the unique name of the template used, if any
  * @param subject       - the message's subject line
  * @param email         - the recipient email address
  * @param tags          - list of tags on this message
  * @param opens         - how many times has this message been opened
  * @param opens_detail  - list of individual opens for the message
  * @param clicks        - how many times has a link been clicked in this message
  * @param clicks_detail - list of individual clicks for the message
  * @param state         - sending status of this message: sent, bounced, rejected
  */
case class MSearchResponse(ts: Int,
                           _id: String,
                           sender: String,
                           template: Option[String],
                           subject: String,
                           email: String,
                           tags: List[String],
                           opens: Int,
                           opens_detail: List[MOpenDetail],
                           clicks: Int,
                           clicks_detail: List[MClickDetails],
                           state: String)
case object MSearchResponse {
  implicit val reads = Json.reads[MSearchResponse]
}

/**
  * Information about a specific smtp event
  *
  * @param ts     - the Unix timestamp when the event occured
  * @param `type` - the message's state as a result of this event
  * @param diag   - the SMTP response from the recipient's server
  */
case class MSmtpEvent(ts: Int, `type`: String, diag: String)
case object MSmtpEvent {
  implicit val reads = Json.reads[MSmtpEvent]
}

// TODO: Missing metadata
/**
  * The information for the message
  *
  * @param ts            - the Unix timestamp from when this message was sent
  * @param _id           - the message's unique id
  * @param sender        - the email address of the sender
  * @param template      - the unique name of the template used, if any
  * @param subject       - the message's subject line
  * @param email         - the recipient email address
  * @param tags          - list of tags on this message
  * @param opens         - how many times has this message been opened
  * @param opens_detail  - list of individual opens for the message
  * @param clicks        - how many times has a link been clicked in this message
  * @param clicks_detail - list of individual clicks for the message
  * @param state         - sending status of this message: sent, bounced, rejected
  * @param smtp_events   - a log of up to 3 smtp events for the message
  */
case class MMessageInfoResponse(ts: Int,
                                _id: String,
                                sender: String,
                                template: Option[String],
                                subject: String,
                                email: String,
                                tags: List[String],
                                opens: Int,
                                opens_detail: List[MOpenDetail],
                                clicks: Int,
                                clicks_detail: List[MClickDetails],
                                state: String,
                                smtp_events: List[MSmtpEvent])
case object MMessageInfoResponse {
  implicit val reads = Json.reads[MMessageInfoResponse]
}


/**
  * The stats for a single hour
  *
  * @param time          - the hour as a UTC date string in YYYY-MM-DD HH:MM:SS format
  * @param sent          - the number of emails that were sent during the hour
  * @param hard_bounces  - the number of emails that hard bounced during the hour
  * @param soft_bounces  - the number of emails that soft bounced during the hour
  * @param rejects       - the number of emails that were rejected during the hour
  * @param complaints    - the number of spam complaints received during the hour
  * @param unsubs        - the number of unsubscribes received during the hour
  * @param opens         - the number of emails opened during the hour
  * @param unique_opens  - the number of unique opens generated by messages sent during the hour
  * @param clicks        - the number of tracked URLs clicked during the hour
  * @param unique_clicks - the number of unique clicks generated by messages sent during the hour
  */
case class MTimeSeriesResponse(time: String,
                               sent: Int,
                               hard_bounces: Int,
                               soft_bounces: Int,
                               rejects: Int,
                               complaints: Int,
                               unsubs: Option[Int] = None,
                               opens: Int,
                               unique_opens: Int,
                               clicks: Int,
                               unique_clicks: Int)
case object MTimeSeriesResponse {
  implicit val reads = Json.reads[MTimeSeriesResponse]
}

/**
  * The sending results for a single recipient
  *
  * @param _id           - the message's unique id
  * @param email         - the email address of the recipient
  * @param status        - the sending status of the recipient - either "sent", "queued", "scheduled", "rejected", or "invalid"
  * @param reject_reason - the reason for the rejection if the recipient status is "rejected"
  */
case class MSendResponse(_id: String,
                         email: String,
                         status: String,
                         reject_reason: Option[String])
case object MSendResponse {
  implicit val reads = Json.reads[MSendResponse]
}


/**
  * The message recipient's information
  *
  * @param email - the email address of the recipient
  * @param name  - the alias of the recipient (if any)
  */
case class MToResponse(email: String, name: Option[String])
case object MToResponse {
  implicit val reads = Json.reads[MToResponse]
}

//TODO: headers
/**
  * The content of the message
  *
  * @param ts         - the Unix timestamp from when this message was sent
  * @param _id        - the message's unique id
  * @param from_email - the email address of the sender
  * @param from_name  - the alias of the sender (if any)
  * @param subject    - the message's subject line
  * @param to         - the message recipient's information
  * @param tags       - list of tags on this message
  * @param text       - the text part of the message, if any
  * @param html       - the HTML part of the message, if any
  * @param attachemnt - an array of any attachments that can be found in the message
  */
case class MContentResponse(ts: Int,
                            _id: String,
                            from_email: String,
                            from_name: String,
                            subject: String,
                            to: MToResponse,
                            tags: List[String],
                            text: String,
                            html: Option[String],
                            attachments: List[MAttachmetOrImage])
case object MContentResponse {
  implicit val reads = Json.reads[MContentResponse]
}

//TODO: missing headers
/**
  * The parsed message
  *
  * @param subject     - the subject of the message
  * @param from_email  - the email address of the sender
  * @param from_name   - the alias of the sender (if any)
  * @param to          - an array of any recipients in the message
  * @param text        - the text part of the message, if any
  * @param html        - the HTML part of the message, if any
  * @param attachments - an array of any attachments that can be found in the message
  * @param images      - an array of any embedded images that can be found in the message
  */
case class MParseResponse(subject: Option[String],
                          from_email: Option[String],
                          from_name: Option[String],
                          to: Option[List[MToResponse]],
                          text: Option[String],
                          html: Option[String],
                          attachments: Option[List[MAttachmetOrImage]],
                          images: Option[List[MAttachmetOrImage]])
case object MParseResponse {
  implicit val reads = Json.reads[MParseResponse]
}

/**
  * A scheduled email
  *
  * @param _id        - the scheduled message id
  * @param created_at - the UTC timestamp when the message was created, in YYYY-MM-DD HH:MM:SS format
  * @param send_at    - the UTC timestamp when the message will be sent, in YYYY-MM-DD HH:MM:SS format
  * @param from_email - the email's sender address
  * @param to         - the email's recipient
  * @param subject    - the email's subject
  */
case class MScheduleResponse(_id: String,
                             created_at: String,
                             send_at: String,
                             from_email: String,
                             to: String,
                             subject: String)
case object MScheduleResponse {
  implicit val reads = Json.reads[MScheduleResponse]
}



