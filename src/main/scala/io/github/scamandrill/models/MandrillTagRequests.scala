package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * An existing tag
  *
  * @param tag - an existing tag name
  */
case class MTagRequest(tag: String)
case object MTagRequest {
  implicit val writes = Json.writes[MTagRequest]
}

