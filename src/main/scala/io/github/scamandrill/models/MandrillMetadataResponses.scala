package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The metadata information
  *
  * @param name          - the unique identifier of the metadata field to update
  * @param state         - the current state of the metadata field, one of "active", "delete", or "index"
  * @param view_template - Mustache template to control how the metadata is rendered in your activity log
  */
case class MIMetadataResponse(name: String,
                              state: String,
                              view_template: String)
case object MIMetadataResponse {
  implicit val reads = Json.reads[MIMetadataResponse]
}
