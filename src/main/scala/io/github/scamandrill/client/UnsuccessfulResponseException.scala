package io.github.scamandrill.client

import play.api.libs.json.JsValue
import play.api.libs.ws.StandaloneWSResponse

class UnsuccessfulResponseException(
                                     val response: StandaloneWSResponse,
                                     val error: Option[JsValue] = None) extends RuntimeException {}
