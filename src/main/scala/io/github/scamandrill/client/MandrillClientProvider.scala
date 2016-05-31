package io.github.scamandrill.client

import javax.inject.Provider

import io.github.scamandrill.models.DefaultConfig
import io.github.scamandrill.utils.SimpleLogger
import play.api.libs.ws.WSClient
import play.api.libs.json.{JsString, JsValue, Writes}

import scala.concurrent.{ExecutionContext, Future}

trait MandrillClientProvider extends SimpleLogger with Provider[MandrillClient] {
  val ws: WSClient
  val onShutdown: () => Future[Unit]

  /**
    * Closes the underlying WSClient.
    * Users of this class are supposed to call
    * this method when they are finished using Scamandrill.
    */
  def shutdown(): Future[Unit] = {
    ws.close()
    onShutdown()
  }

  implicit val ec: ExecutionContext
  override def get(): MandrillClient = {
    getClient()
  }

  def getClient(key: APIKey = APIKey(DefaultConfig.defaultKeyFromConfig)): MandrillClient = {
    new MandrillClient(ws = ws, key = key)
  }

}

case class APIKey(key: String)
case object APIKey {
  implicit val writes = new Writes[APIKey] {
    override def writes(key: APIKey): JsValue = JsString(key.key)
  }
}