package io.github.scamandrill

import com.typesafe.config.ConfigFactory
import io.github.scamandrill.client.{MandrillClient, Scamandrill}
import io.github.scamandrill.utils.SimpleLogger
import mockws.MockWS
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.mvc.{Action, Results}
import play.api.test.Helpers._
import play.api.libs.json.Json

import scala.concurrent.ExecutionContext

trait MandrillSpec extends FlatSpec with Matchers with SimpleLogger with ScalaFutures with BeforeAndAfterAll {
  this: Suite =>
  private val scamandrill = Scamandrill()

  def defaultTimeout = {
    Configuration(ConfigFactory.load("application.conf")).getInt("mandrill.timoutInSeconds") match {
      case Some(t) => timeout(Span(t, Seconds))
      case None =>
        fail("Unable to load mandrill.timeoutInSeconds from application.conf")
    }
  }

  def SCAMANDRILL_API_KEY = sys.env.get("SCAMANDRILL_API_KEY")
  val client: Option[MandrillClient] = SCAMANDRILL_API_KEY.map(scamandrill.getClient)

  def withMockClient(path: String, returnError: Boolean = false, raiseException: Boolean = false)(f: (WSClient) => Unit) = {
    f(MockWS {
      case (POST, p) if p == s"https://mandrillapp.com/api/1.0$path" => Action(request => {
        (request.body.asJson, Option(this.getClass.getClassLoader.getResourceAsStream(s"requests$path")).map(Json.parse)) match {
          case (Some(actual), Some(expected)) =>
            actual shouldBe expected
          case _ => fail(s"Unable to get actual and expected requests for $path")
        }
        if(returnError) {
          Results.InternalServerError.sendResource(s"errors$path")
        } else if(raiseException) {
          throw new RuntimeException("This is a simulated unhandled exception")
        } else {
          Results.Ok.sendResource(s"responses$path")
        }
      })
      case _ => Action(request => fail(s"expected: https://mandrillapp.com/api/1.0$path, actual: ${request.uri}"))
    })
  }

  import scala.concurrent.ExecutionContext.global
  implicit val ec: ExecutionContext = global

  override def afterAll(): Unit = {
    scamandrill.shutdown()
  }

}
