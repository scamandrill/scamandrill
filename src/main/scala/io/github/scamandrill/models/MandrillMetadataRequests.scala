package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The metadata
  *
  * @param name          - a unique identifier for the metadata field
  * @param view_template - optional Mustache template to control how the metadata is rendered in your activity log
  */
case class MMeteadatapAdd(name: String,
                          view_template: String)
case object MMeteadatapAdd {
  implicit val writes = Json.writes[MMeteadatapAdd]
}

/**
  * The metadata
  *
  * @param name - the unique identifier of the metadata field to update
  */
case class MMeteadatapDelete(name: String)
case object MMeteadatapDelete {
  implicit val writes = Json.writes[MMeteadatapDelete]
}
