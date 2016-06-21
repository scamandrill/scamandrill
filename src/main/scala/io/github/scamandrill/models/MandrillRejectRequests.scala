package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The mail to be blacklisted
  *
  * @param email      - the email address to add to the blacklist
  * @param comment    - an optional comment describing the rejection
  * @param subaccount an optional unique identifier for the subaccount to limit the blacklist entry
  */
case class MRejectAdd(email: String,
                      comment: Option[String] = None,
                      subaccount: Option[String] = None)
case object MRejectAdd {
  implicit val writes = Json.writes[MRejectAdd]
}

/**
  * Information about the list of mail that are blacklisted to be retrieved
  *
  * @param email           - the email that is blacklisted
  * @param include_expired - whether to include rejections that have already expired.
  * @param subaccount      an optional unique identifier for the subaccount to limit the blacklist entry
  */
case class MRejectList(email: String,
                       include_expired: Boolean = false,
                       subaccount: Option[String] = None)
case object MRejectList {
  implicit val writes = Json.writes[MRejectList]
}

/**
  * The mail to be removed from the blacklist
  *
  * @param email      - the email address to remove from the blacklist
  * @param subaccount an optional unique identifier for the subaccount to limit the blacklist entry
  */
case class MRejectDelete(email: String,
                         subaccount: Option[String] = None)
case object MRejectDelete {
  implicit val writes = Json.writes[MRejectDelete]
}


