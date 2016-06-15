package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The export information
  *
  * @param id  - an export job identifier
  */
case class MExportInfo(id: String)
case object MExportInfo {
  implicit val writes = Json.writes[MExportInfo]
}

/**
  * The export notify info
  *
  * @param notify_email - an optional email address to notify when the export job has finished
  */
case class MExportNotify(notify_email: String)
case object MExportNotify {
  implicit val writes = Json.writes[MExportNotify]
}

/**
  * The export activity
  *
  * @param notify_email - an optional email address to notify when the export job has finished
  * @param date_from    - start date as a UTC string in YYYY-MM-DD HH:MM:SS format
  * @param date_to      - end date as a UTC string in YYYY-MM-DD HH:MM:SS format
  * @param tags         - an array of tag names to narrow the export to; will match messages that contain ANY of the tags
  * @param senders      - an array of senders to narrow the export to
  * @param states       - an array of states to narrow the export to; messages with ANY of the states will be included
  * @param api_keys     - an array of api keys to narrow the export to; messsagse sent with ANY of the keys will be included
  */
case class MExportActivity(notify_email: String,
                           date_from: String,
                           date_to: String,
                           tags: List[String],
                           senders: List[String],
                           states: List[String],
                           api_keys: List[String])
case object MExportActivity {
  implicit val writes = Json.writes[MExportActivity]
}

