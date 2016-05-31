package io.github.scamandrill.client

import play.api.libs.json.Json

case class MandrillError(status: String, code: Int, name: String, message: String)
case object MandrillError {
  implicit val reads = Json.reads[MandrillError]
}

case class MandrillResponseException(httpCode: Int,
                                     httpReason: String,
                                     mandrillError: MandrillError) extends RuntimeException {}
