package io.github.scamandrill.client

import play.api.libs.ws.WSClient
import play.api.libs.json.{JsValue, Writes, JsString}

trait MandrillClientProvider {
  val wc: WSClient

  def `with`(key: APIKey): MandrillClient = {
    new MandrillClient(wc = wc, key = key)
  }
}

case class APIKey(key: String)
case object APIKey {
  implicit val writes = new Writes[APIKey] {
    override def writes(key: APIKey): JsValue = JsString(key.key)
  }
}