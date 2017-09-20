package io.github.scamandrill.client

import javax.inject.Provider

import com.typesafe.config.ConfigFactory
import io.github.scamandrill.utils.SimpleLogger
import play.api.libs.json.{JsString, JsValue, Writes}
import play.api.libs.ws.StandaloneWSClient

import scala.concurrent.{ExecutionContext, Future}
import scala.util.Try

trait MandrillClientProvider extends SimpleLogger with Provider[MandrillClient] {
  val ws: StandaloneWSClient
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

  def getClient(key: APIKey = APIKey()): MandrillClient = {
    new MandrillClient(ws = ws, key = key)
  }

  def getClient(key: String): MandrillClient = {
    getClient(APIKey(key))
  }

}

case class APIKey(key: String = APIKey.defaultKey)
case object APIKey extends SimpleLogger {
  implicit val writes: Writes[APIKey] = new Writes[APIKey] {
    override def writes(o: APIKey): JsValue = JsString(o.key)
  }

  def defaultKey: String = {
    Try {
      ConfigFactory.load("application.conf").getString("mandrill.key")
    }.getOrElse {
        logger.error("Unable to locate mandrill.key in application.conf")
        ""
    }
  }
}