package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * An email to be retrieved or deleted from the whitelist
  *
  * @param email - an email address for the whitelist
  */
case class MWhitelist(email: String)
case object MWhitelist {
  implicit val writes = Json.writes[MWhitelist]
}

/**
  * An entry for the whitelist
  * @param email - the email address to be added to the whitelist
  * @param comment - a comment regarding this whitelisting
  */
case class MWhitelistAdd(email: String, comment: Option[String])
case object MWhitelistAdd {
  implicit val writes = Json.writes[MWhitelistAdd]
}
