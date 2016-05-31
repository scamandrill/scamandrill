package io.github.scamandrill.client

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import play.api.{Configuration, Environment, Mode}
import play.api.libs.ws.{WSClient, WSConfigParser}
import play.api.libs.ws.ahc.{AhcConfigBuilder, AhcWSClient, AhcWSClientConfigParser}

import scala.concurrent.{ExecutionContext, Future}

class Scamandrill(val ws: WSClient, val ec: ExecutionContext, val onShutdown: () => Future[Unit]) extends MandrillClientProvider {
}

object Scamandrill extends ((WSClient, ExecutionContext, () => Future[Unit]) => Scamandrill) {

  override def apply(ws: WSClient, ec: ExecutionContext, onShutdown: () => Future[Unit] = () => Future.successful(())): Scamandrill = new Scamandrill(ws, ec, onShutdown)
  def apply(ws: WSClient): Scamandrill = apply(ws, scala.concurrent.ExecutionContext.global)
  def apply(ws: WSClient)(implicit ec: ExecutionContext): Scamandrill = apply(ws, ec)

  /**
    * If no WSClient is provided to Scamandrill, it is the user's responsibility to shutdown Scamandrill when finished.
    * @param configuration this configuration is used to override the default values for the WSClient that Scamandrill constructs
    * @return
    */
  def apply(configuration: Configuration): Scamandrill = {
    implicit val system = ActorSystem("scamandrill")
    implicit val mat = ActorMaterializer()
    val env: Environment = Environment.simple(mode = Mode.Prod)

    val _configuration = Configuration.reference ++ Configuration(ConfigFactory.load("io.github.scamandrill.conf")) ++ configuration

    val wsParser = new WSConfigParser(_configuration, env)
    val ahcParse = new AhcWSClientConfigParser(wsParser.parse(), _configuration, env)
    val builder = new AhcConfigBuilder(ahcParse.parse())

    apply(new AhcWSClient(builder.configure().build()), scala.concurrent.ExecutionContext.global, () => {
      system.terminate().map(_ => ())(scala.concurrent.ExecutionContext.global)
    })
  }
  def apply():Scamandrill = apply(Configuration.empty)
}