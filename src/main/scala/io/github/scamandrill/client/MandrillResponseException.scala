package io.github.scamandrill.client

import play.api.libs.json.{JsObject, Json, Writes, Reads}
import play.api.libs.ws.StandaloneWSResponse

case class MandrillError(status: String, code: Int, name: String, message: String)
case object MandrillError {
  implicit val reads: Reads[MandrillError] = Json.reads[MandrillError]
  implicit val writes: Writes[MandrillError] = Json.writes[MandrillError]
}

case class MandrillResponseException(override val response: StandaloneWSResponse,
                                     mandrillError: MandrillError) extends UnsuccessfulResponseException(response) {}
