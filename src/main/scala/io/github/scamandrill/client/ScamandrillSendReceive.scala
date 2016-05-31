package io.github.scamandrill.client

import play.api.libs.ws.WSClient
import akka.actor.ActorSystem
import akka.http.scaladsl._
import akka.http.scaladsl.model._
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.{Sink, Source}
import io.github.scamandrill.utils.SimpleLogger
import play.api.http.Writeable
import play.api.libs.json._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success}

/**
  * This trait abstract on top of spray the handling of all request / response to the mandrill API. Its
  * executeQuery function is the one used by the client.
  */
trait ScamandrillSendReceive extends SimpleLogger {

  private val serviceRoot: String = "https://mandrillapp.com/api/1.0/"
  type Entity = Either[Throwable, RequestEntity]

  val wc: WSClient
  val key: APIKey

  private def authenticatedWriter[T](ow: Writes[T]): Writes[T] = {
    new Writes[T] {
      override def writes(o: T): JsValue = ow.writes(o) match {
        case js: JsObject => js + ("key" -> Json.toJson(key))
        case value => value
      }
    }
  }

  /**
    * Fire a request to Mandrill API and try to parse the response. Because it return a Future[S], the
    * unmarshalling of the response body is done via a partially applied function on the Future transformation.
    * Uses spray-can internally to fire the request and unmarshall the response, with spray-json to do that.
    *
    * @param endpoint - the Mandrill API endpoint for the operation, for example '/messages/send.json'
    * @param reqBody  - the body of the post request already marshalled as json
    * @param handler  - this is the unmarshaller fuction of the response body, partially applied function
    * @tparam S - the type of the expected body response once unmarshalled
    * @return - a future of the expected type S
    * @note as from the api documentation, all requests are POST, and You can consider any non-200 HTTP
    *       response code an error - the returned data will contain more detailed information
    */
  def executeQuery[Req, Res](endpoint: String, model: Req)(implicit writes: Writes[Req], reads: Reads[Res]): Future[Res] = {
    wc.url(s"$serviceRoot$endpoint").post(Json.toJson(model)(authenticatedWriter(writes))).map { res =>
      res.json.validate[Res](reads).fold(
        invalid = error => {
          ""
        },
        valid = response => {

        }
      )
    }
  }

  /**
    * Asks all the underlying actors to close (waiting for 1 second)
    * and then shut down the system. Users of this class are supposed to call
    * this method when they are no-longer required or the application exits.
    */
  def shutdown(): Unit = {
    logger.info("asking all actor to close")
    logger.info("actor system shut down")
  }
}
