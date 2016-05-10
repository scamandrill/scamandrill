package io.github.scamandrill.client

import akka.http.scaladsl.unmarshalling._
import akka.stream.Materializer
import spray.json._

import scala.concurrent.{ExecutionContext, Future}


case class MandrillError(status: String, code: Int, name: String, message: String)

case class MandrillResponseException(httpCode: Int,
                                     httpReason: String,
                                     mandrillError: MandrillError) extends RuntimeException {}

object MandrillResponseExceptionJsonProtocol extends DefaultJsonProtocol {
  implicit val MandrillErrorj = jsonFormat4(MandrillError)
}

object MandrillResponseException {

  def apply(ex: UnsuccessfulResponseException)(implicit mat: Materializer, ec: ExecutionContext): Future[MandrillResponseException] = {

    import MandrillResponseExceptionJsonProtocol._
    import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._

    Unmarshal(ex.response.entity).to[MandrillError].map { me =>
      new MandrillResponseException(
        ex.response.status.intValue,
        ex.response.status.reason,
        me
      )
    }
  }
}
