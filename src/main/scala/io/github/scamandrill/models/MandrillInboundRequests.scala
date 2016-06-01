package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The inbound domain
  *
  * @param domain - a domain name
  */
case class MInboundDomain(domain: String) extends MandrillRequest
case object MInboundDomain {
  implicit val writes = Json.writes[MInboundDomain]
}

/**
  * The Route
  *
  * @param domain  - a domain name
  * @param pattern - the search pattern that the mailbox name should match
  * @param url     - the webhook URL where the inbound messages will be published
  */
case class MInboundRoute(domain: String,
                         pattern: String,
                         url: String) extends MandrillRequest
case object MInboundRoute {
  implicit val writes = Json.writes[MInboundRoute]
}


/**
  * The Route
  *
  * @param id      - the unique identifier of an existing mailbox route
  * @param pattern - the search pattern that the mailbox name should match
  * @param url     - the webhook URL where the inbound messages will be published
  */
case class MInboundUpdateRoute(id: String,
                               pattern: String,
                               url: String) extends MandrillRequest
case object MInboundUpdateRoute {
  implicit val writes = Json.writes[MInboundUpdateRoute]
}

/**
  * The route
  *
  * @param id  - the unique identifier of an existing route
  */
case class MInboundDelRoute(id: String) extends MandrillRequest
case object MInboundDelRoute {
  implicit val writes = Json.writes[MInboundDelRoute]
}

/**
  * Inbound raw
  *
  * @param raw_message    - the full MIME document of an email message
  * @param to             - optionally define the recipients to receive the message - otherwise we'll use the To, Cc, and Bcc headers provided in the document
  * @param mail_from      - the address specified in the MAIL FROM stage of the SMTP conversation. Required for the SPF check.
  * @param helo           - the identification provided by the client mta in the MTA state of the SMTP conversation. Required for the SPF check.
  * @param client_address - the remote MTA's ip address. Optional; required for the SPF check.
  */
case class MInboundRaw(raw_message: String,
                       to: List[String],
                       mail_from: String,
                       helo: String,
                       client_address: String) extends MandrillRequest
case object MInboundRaw {
  implicit val writes = Json.writes[MInboundRaw]
}


