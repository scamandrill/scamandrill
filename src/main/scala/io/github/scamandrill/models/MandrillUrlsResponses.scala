package io.github.scamandrill.models

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * Details about the domain's CNAME record
  *
  * @param valid       - whether the domain's CNAME record is valid for use with Mandrill
  * @param valid_after - when the domain's CNAME record will be considered valid for use with Mandrill. If set, this indicates that the record is valid now, but was previously invalid, and Mandrill will wait until the record's TTL elapses to start using it.
  * @param error       - an error describing the CNAME record, or null if the record is correct
  */
case class MUrlCname(valid: Boolean,
                     valid_after: Option[DateTime],
                     error: Option[String])
case object MUrlCname {
  implicit val dt = MandrillDateFormats.DATETIME_FORMAT
  implicit val reads = Json.reads[MUrlCname]
}

/**
  * The individual URL stats
  *
  * @param url           - he URL to be tracked
  * @param sent          - the number of emails that contained the URL
  * @param clicks        - the number of times the URL has been clicked from a tracked email
  * @param unique_clicks - the number of unique emails that have generated clicks for this URL
  */
case class MUrlResponse(url: String,
                        sent: Int,
                        clicks: Int,
                        unique_clicks: Int)
case object MUrlResponse {
  implicit val reads = Json.reads[MUrlResponse]
}

/**
  * The information for a single hour
  *
  * @param time          - the hour
  * @param sent          - the number of emails that contained the URL
  * @param clicks        - the number of times the URL has been clicked from a tracked email
  * @param unique_clicks - the number of unique emails that have generated clicks for this URL
  */
case class MUrlTimeResponse(time: DateTime,
                            sent: Int,
                            clicks: Int,
                            unique_clicks: Int)
case object MUrlTimeResponse {
  implicit val dt = MandrillDateFormats.DATETIME_FORMAT
  implicit val reads = Json.reads[MUrlTimeResponse]
}

/**
  * The individual tracking domain
  *
  * @param domain         - the tracking domain name
  * @param created_at     - when the tracking domain was added
  * @param last_tested_at - when the domain's DNS settings were last tested
  * @param valid_tracking - whether this domain can be used as a tracking domain for email.
  * @param cname          - details about the domain's CNAME record
  */
case class MUrlDomainResponse(domain: String,
                              created_at: DateTime,
                              last_tested_at: DateTime,
                              valid_tracking: Boolean,
                              cname: MUrlCname)
case object MUrlDomainResponse {
  implicit val dt = MandrillDateFormats.DATETIME_FORMAT
  implicit val reads = Json.reads[MUrlDomainResponse]
}



