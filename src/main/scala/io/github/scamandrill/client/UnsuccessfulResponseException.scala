package io.github.scamandrill.client

import play.api.libs.ws.WSResponse

class UnsuccessfulResponseException(val response: WSResponse) extends RuntimeException {}
