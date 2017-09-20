package io.github.scamandrill.client

import play.api.libs.ws.{StandaloneWSClient, StandaloneWSResponse}
import io.github.scamandrill.client.MandrillClient.Endpoints.Endpoint
import io.github.scamandrill.utils.SimpleLogger
import play.api.libs.json._
import play.api.libs.ws.JsonBodyReadables._
import play.api.libs.ws.JsonBodyWritables._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

/**
  * This trait abstract on top of spray the handling of all request / response to the mandrill API. Its
  * executeQuery function is the one used by the client.
  */
trait ScamandrillSendReceive extends SimpleLogger {

  private val serviceRoot: String = "https://mandrillapp.com/api/1.0"

  val ws: StandaloneWSClient
  val key: APIKey
  implicit val ec: ExecutionContext

  private def authenticatedWriter[T](ow: Writes[T]): Writes[T] = new Writes[T] {
    override def writes(o: T): JsValue = ow.writes(o) match {
      case js: JsObject => js + ("key" -> Json.toJson(key))
      case value => value
    }
  }

  /**
    * Fire a request to Mandrill API and try to parse the response.
    *
    * @param endpoint - the Mandrill API endpoint for the operation, for example '/messages/send.json'
    * @return - a future MandrillResponse
    * @note as from the api documentation, all requests are POST, and You can consider any non-200 HTTP
    *       response code an error - the returned data will contain more detailed information
    */
  def executeQuery[Req, Res](endpoint: Endpoint, model: Req)(implicit writes: Writes[Req], reads: Reads[Res]): Future[Try[Res]] = {
    def handleError(res: StandaloneWSResponse, error: Option[JsValue]): Try[Res] = {
      res.body[JsValue].validate[MandrillError].fold(
        invalid = _ => Failure(new UnsuccessfulResponseException(res, error)),
        valid = me => Failure(MandrillResponseException(res, me))
      )
    }
    ws.url(s"$serviceRoot$endpoint").withFollowRedirects(true).post(Json.toJson(model)(authenticatedWriter(writes))) map {
      case res if res.status == 200 =>
        res.body[JsValue].validate[Res](reads).fold(
          invalid = jsError => {
            logger.debug("Error parsing json from mandrill: " + Json.stringify(JsError.toJson(jsError)))
            handleError(res, Some(JsError.toJson(jsError)))
          },
          valid = Success.apply
        )
      case res => handleError(res, None)
    } recover {
      case e => Failure(e)
    }

  }
}
