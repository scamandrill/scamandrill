package io.github.scamandrill.client

import play.api.libs.json.{JsObject, Json}
import play.api.libs.ws.WSResponse

case class MandrillError(status: String, code: Int, name: String, message: String)
case object MandrillError {
  implicit val reads = Json.reads[MandrillError]
  implicit val writes = Json.writes[MandrillError]
}

case class MandrillResponseException(override val response: WSResponse,
                                     mandrillError: MandrillError) extends UnsuccessfulResponseException(response) {}
