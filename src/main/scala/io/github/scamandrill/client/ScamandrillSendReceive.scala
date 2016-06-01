package io.github.scamandrill.client

import play.api.libs.ws.{WSClient, WSResponse}
import io.github.scamandrill.client.MandrillClient.Endpoints.Endpoint
import io.github.scamandrill.utils.SimpleLogger
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}
import scala.language.postfixOps

/**
  * This trait abstract on top of spray the handling of all request / response to the mandrill API. Its
  * executeQuery function is the one used by the client.
  */
trait ScamandrillSendReceive extends SimpleLogger {

  private val serviceRoot: String = "https://mandrillapp.com/api/1.0/"

  val ws: WSClient
  val key: APIKey
  implicit val ec: ExecutionContext

  private def authenticatedWriter[T](ow: Writes[T]): Writes[T] = {
    new Writes[T] {
      override def writes(o: T): JsValue = ow.writes(o) match {
        case js: JsObject => js + ("key" -> Json.toJson(key))
        case value => value
      }
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
  def executeQuery[Req, Res](endpoint: Endpoint, model: Req)(implicit writes: Writes[Req], reads: Reads[Res]): Future[MandrillResponse[Res]] = {
    def handleError(res: WSResponse): MandrillResponse[Res] = {
      res.json.validate[MandrillError].fold(
        invalid = _ => MandrillFailure(new Exception("Unable to parse response")),
        valid = me => MandrillFailure[Res](new MandrillResponseException(res.status, res.body, me))
      )
    }

    ws.url(s"$serviceRoot$endpoint").post(Json.toJson(model)(authenticatedWriter(writes))) map {
      case res if res.status == 200 =>
        res.json.validate[Res](reads).fold(
          invalid = _ => handleError(res),
          valid = MandrillSuccess.apply
        )
      case res => handleError(res)
    } recover {
      case e => MandrillFailure[Res](e)
    }

  }
}
