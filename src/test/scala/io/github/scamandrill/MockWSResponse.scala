package io.github.scamandrill

import akka.stream.scaladsl.Source
import akka.util.ByteString
import play.api.libs.ws.{StandaloneWSResponse, WSCookie}

case class MockWSResponse(
  bytes: Option[ByteString],
  status: Int,
  headers: Map[String, Seq[String]],
  statusText: String,
  cookies: Seq[WSCookie]
) extends StandaloneWSResponse {
  override def underlying[T]: T = ???

  override def cookie(name: String): Option[WSCookie] = cookies.find(_.name == name)

  override def body: String = bytes.map(_.utf8String).getOrElse("")

  override def bodyAsBytes: ByteString = bytes.getOrElse(ByteString())

  override def bodyAsSource: Source[ByteString, _] = Source.single(bodyAsBytes)
}