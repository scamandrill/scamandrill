package io.github.scamandrill.client

import play.api.{Configuration, Environment, Mode}
import play.api.libs.ws.{WSClient, WSConfigParser}
import play.api.libs.ws.ning.{NingAsyncHttpClientConfigBuilder, NingWSClient, NingWSClientConfig, NingWSClientConfigParser}

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
    val env: Environment = Environment.simple(mode = Mode.Prod)

    val _configuration = Configuration.reference ++ configuration

    val parser = new NingWSClientConfigParser(
      wsClientConfig = new WSConfigParser(_configuration, env).parse(),
      configuration = _configuration,
      environment = env
    )

    val builder = new NingAsyncHttpClientConfigBuilder(parser.parse())

    new Scamandrill(
      ws = new NingWSClient(builder.configure().build()),
      ec = scala.concurrent.ExecutionContext.global,
      onShutdown = () => Future.successful(())
    )
  }
  def apply():Scamandrill = apply(Configuration.empty)
}