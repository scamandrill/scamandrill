package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The sender domain
  *
  * @param domain - a domain name at which you can receive email
  */
case class MSenderDomain(domain: String)
case object MSenderDomain {
  implicit val writes = Json.writes[MSenderDomain]
}

/**
  * The sender domain
  *
  * @param domain  - a domain name at which you can receive email
  * @param mailbox - a mailbox at the domain where the verification email should be sent
  */
case class MSenderVerifyDomain(domain: String,
                               mailbox: String)
case object MSenderVerifyDomain {
  implicit val writes = Json.writes[MSenderVerifyDomain]
}

/**
  * The sender address
  *
  * @param address - the email address of the sender
  */
case class MSenderAddress(address: String)
case object MSenderAddress {
  implicit val writes = Json.writes[MSenderAddress]
}
