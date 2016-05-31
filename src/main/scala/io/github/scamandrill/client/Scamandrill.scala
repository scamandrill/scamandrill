package io.github.scamandrill.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import play.api.{Configuration, Environment, Mode}
import play.api.libs.ws.{WSClient, WSConfigParser}
import play.api.libs.ws.ahc.{AhcConfigBuilder, AhcWSClient, AhcWSClientConfig}

import scala.concurrent.{ExecutionContext, Future}

class Scamandrill(val ws: WSClient, val ec: ExecutionContext, val onShutdown: () => Future[Unit]) extends MandrillClientProvider {
}

object Scamandrill extends ((WSClient, ExecutionContext, () => Future[Unit]) => Scamandrill) {

  override def apply(ws: WSClient, ec: ExecutionContext, onShutdown: () => Future[Unit] = () => Future.successful(())): Scamandrill = new Scamandrill(ws, ec, onShutdown)
  def apply(ws: WSClient): Scamandrill = apply(ws, scala.concurrent.ExecutionContext.global)
  def apply(ws: WSClient)(implicit ec: ExecutionContext): Scamandrill = apply(ws, ec)
  def apply(implicit ec: ExecutionContext): Scamandrill = {
    implicit val system = ActorSystem("scamandrill")
    implicit val mat = ActorMaterializer()
    val configuration = Configuration.reference ++ Configuration(ConfigFactory.load("io.github.scamandrill.conf"))
    val parser = new WSConfigParser(configuration, Environment.simple(mode = Mode.Prod))
    val config = new AhcWSClientConfig(wsClientConfig = parser.parse())
    val builder = new AhcConfigBuilder(config)

    apply(new AhcWSClient(builder.configure().build()), ec, () => {
      system.terminate().map(_ => ())
    })
  }
  def apply(): Scamandrill = apply(scala.concurrent.ExecutionContext.global)
}