package io.github.scamandrill.models

import org.joda.time.DateTime
import play.api.libs.json.Json

//TODO: not whether as in documentation
/**
  *
  * @param email - the email address you provided
  * @param added - true if the operation succeeded
  */
case class MWhitelistAddResponse(email: String, added: Boolean)
case object MWhitelistAddResponse {
  implicit val reads = Json.reads[MWhitelistAddResponse]
}

/**
  * The information for each whitelist entry
  *
  * @param email      - the email that is whitelisted
  * @param detail     - a description of why the email was whitelisted
  * @param created_at - when the email was added to the whitelist
  */
case class MWhitelistListResponse(email: String,
                                  detail: String,
                                  created_at: DateTime)
case object MWhitelistListResponse {
  implicit val dt = MandrillDateFormats.DATETIME_FORMAT
  implicit val reads = Json.reads[MWhitelistListResponse]
}

/**
  * Object containing the address and whether the deletion succeeded
  *
  * @param email   - the email address that was removed from the blacklist
  * @param deleted - whether the address was deleted successfully
  */
case class MWhitelistDeleteResponse(email: String, deleted: Boolean)
case object MWhitelistDeleteResponse {
  implicit val reads = Json.reads[MWhitelistDeleteResponse]
}
