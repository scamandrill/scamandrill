package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * An email to be added retrieved or deleted from the whitelist
  *
  * @param email - an email address for the whitelist
  */
case class MWhitelist(email: String)
case object MWhitelist {
  implicit val writes = Json.writes[MWhitelist]
}
