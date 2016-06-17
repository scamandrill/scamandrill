package io.github.scamandrill.client

import play.api.libs.json.JsValue
import play.api.libs.ws.WSResponse

class UnsuccessfulResponseException(val response: WSResponse, val error: Option[JsValue] = None) extends RuntimeException {}
