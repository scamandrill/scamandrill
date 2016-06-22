package io.github.scamandrill.models

import org.joda.time.DateTime
import play.api.libs.json.Json

/**
  * The export response
  *
  * @param id          - the unique identifier for this Export. Use this identifier when checking the export job's status
  * @param created_at  - the date and time that the export job was created format
  * @param `type`      - the type of the export job
  * @param finished_at - when the that the export job was finished, or None for jobs that have not run
  * @param state       - the export job's state
  * @param result_url  - the url for the export job's results, if the job is complete
  */
case class MExportResponse(id: String,
                           created_at: DateTime,
                           `type`: String,
                           finished_at: Option[DateTime],
                           state: String,
                           result_url: Option[String])
case object MExportResponse {
  implicit val dt = MandrillDateFormats.DATETIME_FORMAT
  implicit val reads = Json.reads[MExportResponse]
}

