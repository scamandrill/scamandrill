package io.github.scamandrill

import java.net.URI

import akka.stream.scaladsl.Source
import akka.util.ByteString
import play.api.libs.ws.{BodyWritable, EmptyBody, StandaloneWSClient, StandaloneWSRequest, StandaloneWSResponse, WSAuthScheme, WSBody, WSCookie, WSProxyServer, WSRequestFilter, WSSignatureCalculator}

import scala.concurrent.Future
import scala.concurrent.duration.Duration

case class MockWSRequest(
  test: (StandaloneWSRequest => Future[StandaloneWSResponse]),
  body: WSBody,
  auth: Option[(String, String, WSAuthScheme)],
  followRedirects: Option[Boolean],
  headers: Map[String, Seq[String]],
  contentType: Option[String],
  cookies: Seq[WSCookie],
  virtualHost: Option[String],
  method: String,
  queryString: Map[String, Seq[String]],
  proxyServer: Option[WSProxyServer],
  calc: Option[WSSignatureCalculator],
  url: String,
  uri: URI,
  requestTimeout: Option[Int]
) extends StandaloneWSRequest {

  override type Self = MockWSRequest

  override def withFollowRedirects(follow: Boolean): Self = this.copy(followRedirects = Some(follow))

  override def withHttpHeaders(headers: (String, String)*): Self = {
    val additional: Iterable[(String, Seq[String])] = headers.groupBy(_._1).map {
      case (key, group) => (key, group.map(_._2))
    }

    this.copy(headers = this.headers ++ additional)
  }

  override def sign(calc: WSSignatureCalculator): Self = this

  override def withRequestFilter(filter: WSRequestFilter): Self = this

  override def withCookies(cookies: WSCookie*): Self = this.copy(cookies = this.cookies ++ cookies.toSeq)

  override def put[T](body: T)(implicit writable: BodyWritable[T]): Future[Response] =
    this.withBody(body).execute("PUT")

  override def patch[T](body: T)(implicit writable: BodyWritable[T]): Future[Response] =
    this.withBody(body).execute("PATCH")

  override def post[T](body: T)(implicit writable: BodyWritable[T]): Future[Response] =
    this.withBody(body).execute("POST")

  override def options(): Future[Response] = this.execute("OPTIONS")

  override def execute(method: String): Future[Response] = this.copy(method = method).execute()

  override def execute(): Future[Response] = this.test(this)

  override def withRequestTimeout(timeout: Duration): Self = this.copy(requestTimeout = Some(timeout.toSeconds.toInt))

  override def withProxyServer(proxyServer: WSProxyServer): Self = this

  override def withMethod(method: String): Self = this.copy(method = method)

  override def delete(): Future[Response] = this.execute("DELETE")

  override def head(): Future[Response] = this.execute("HEAD")

  override def stream(): Future[Response] = this.execute()

  override def get(): Future[Response] = this.execute("GET")

  override def withVirtualHost(vh: String): Self = this.copy(virtualHost = Some(vh))

  override def withQueryStringParameters(parameters: (String, String)*): Self = {
    val additional: Iterable[(String, Seq[String])] = parameters.groupBy(_._1).map {
      case (key, group) => (key, group.map(_._2))
    }
    this.copy(queryString = this.queryString ++ additional)
  }

  override def withAuth(username: String, password: String, scheme: WSAuthScheme): Self =
    this.copy(auth = Some((username, password, scheme)))

  override def withBody[T](body: T)(implicit writes: BodyWritable[T]): Self =
    this.copy(body = writes.transform(body))

  override type Response = StandaloneWSResponse

}

case object MockWSRequest {
  def MockWS(test: (StandaloneWSRequest => Future[StandaloneWSResponse])): StandaloneWSClient =
    new StandaloneWSClient {
      override def underlying[T]: T = ???

      override def url(url: String): StandaloneWSRequest = MockWSRequest(
        test = test,
        body = EmptyBody,
        auth = None,
        followRedirects = None,
        headers = Map(),
        contentType = None,
        cookies = Seq(),
        virtualHost = None,
        method = "GET",
        queryString = Map(),
        proxyServer = None,
        calc = None,
        url = url,
        uri = URI.create(url),
        requestTimeout = None
      )

      override def close(): Unit = {}
  }
}
