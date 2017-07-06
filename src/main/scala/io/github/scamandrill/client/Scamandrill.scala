package io.github.scamandrill.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.{Config, ConfigFactory}
import play.api.libs.ws.ahc.{AhcWSClientConfigFactory, StandaloneAhcWSClient}
import play.api.libs.ws.StandaloneWSClient

import scala.concurrent.{ExecutionContext, Future}

class Scamandrill(val ws: StandaloneWSClient, val ec: ExecutionContext, val onShutdown: () => Future[Unit]) extends MandrillClientProvider {
}

object Scamandrill extends ((StandaloneWSClient, ExecutionContext, () => Future[Unit]) => Scamandrill) {

  override def apply(ws: StandaloneWSClient, ec: ExecutionContext, onShutdown: () => Future[Unit] = () => Future.successful(())): Scamandrill = new Scamandrill(ws, ec, onShutdown)
  def apply(ws: StandaloneWSClient): Scamandrill = apply(ws, scala.concurrent.ExecutionContext.global)
  def apply(ws: StandaloneWSClient)(implicit ec: ExecutionContext): Scamandrill = apply(ws, ec)

  /**
    * If no WSClient is provided to Scamandrill, it is the user's responsibility to shutdown Scamandrill when finished.
    * @param configuration this configuration is used to override the default values for the WSClient that Scamandrill constructs
    * @return
    */
  def apply(configuration: Config): Scamandrill = {
    implicit val system = ActorSystem("scamandrill")
    implicit val mat = ActorMaterializer()
    val clientConfig = AhcWSClientConfigFactory.forConfig(configuration.withFallback(ConfigFactory.load()))
    apply(StandaloneAhcWSClient(clientConfig), scala.concurrent.ExecutionContext.global, () => {
      system.terminate().map(_ => ())(scala.concurrent.ExecutionContext.global)
    })
  }

  def apply(): Scamandrill = apply(ConfigFactory.load())
}