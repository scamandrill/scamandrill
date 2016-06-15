package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * Information about the inbound domain
  *
  * @param domain     - the domain name that is accepting mail
  * @param created_at - the date and time that the inbound domain was added as a UTC string in YYYY-MM-DD HH:MM:SS format
  * @param valid_mx   - true if this inbound domain has successfully set up an MX record to deliver mail to the Mandrill servers
  */
case class MInboundDomainResponse(domain: String,
                                  created_at: String,
                                  valid_mx: Boolean)
case object MInboundDomainResponse {
  implicit val reads = Json.reads[MInboundDomainResponse]
}

/**
  * Information about the inbound route
  *
  * @param id      - the unique identifier of the route
  * @param pattern - the mailbox route pattern that the recipient matched
  * @param url     - the webhook URL that the message was posted to
  */
case class MInboundRouteResponse(id: String,
                                 pattern: String,
                                 url: String)
case object MInboundRouteResponse {
  implicit val reads = Json.reads[MInboundRouteResponse]
}

/**
  * The inbound route response
  *
  * @param email   - the email address of the matching recipient
  * @param pattern - the mailbox route pattern that the recipient matched
  * @param url     - the webhook URL that the message was posted to
  */
case class MInboundRawResponse(email: String,
                               pattern: String,
                               url: Boolean)
case object MInboundRawResponse {
  implicit val reads = Json.reads[MInboundRawResponse]
}
