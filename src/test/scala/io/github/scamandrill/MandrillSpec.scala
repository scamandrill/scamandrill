package io.github.scamandrill

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl.StreamConverters
import akka.util.ByteString
import com.typesafe.config.ConfigFactory
import io.github.scamandrill.client.{MandrillClient, Scamandrill}
import io.github.scamandrill.utils.SimpleLogger
import org.scalatest._
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.time.{Seconds, Span}
import play.api.Configuration
import play.api.libs.json.Json
import play.api.libs.ws.{InMemoryBody, StandaloneWSClient, StandaloneWSRequest, StandaloneWSResponse}

import scala.concurrent.{ExecutionContext, Future}

trait MandrillSpec extends FlatSpec with Matchers with SimpleLogger with ScalaFutures with BeforeAndAfterAll {
  this: Suite =>
  val scamandrill = Scamandrill()
  implicit val system = ActorSystem("scamandrill-test")
  implicit val mat = ActorMaterializer()

  val utcDateTimeParser = MandrillTestUtils.utcDateTimeParser
  val dateParser = MandrillTestUtils.dateParser

  def defaultTimeout =
    timeout(Span(Configuration(ConfigFactory.load("application.conf")).get[Int]("mandrill.timoutInSeconds"), Seconds))

  def SCAMANDRILL_API_KEY = sys.env.get("SCAMANDRILL_API_KEY")
  val actualClient: Option[MandrillClient] = SCAMANDRILL_API_KEY.map(scamandrill.getClient)

  def withMockClient(path: String, returnError: Boolean = false, raiseException: Boolean = false)(f: (StandaloneWSClient) => Unit) = {
    def loadFile(prefix: String): Future[ByteString] = {
      StreamConverters
        .fromInputStream(in = () => this.getClass.getClassLoader.getResourceAsStream(s"$prefix$path"))
        .runFold(ByteString())(_ ++ _)
    }

    val test = (request: StandaloneWSRequest) => {
      if (request.url === s"https://mandrillapp.com/api/1.0$path") {
        if (returnError) {
          loadFile("errors").map { result =>
              MockWSResponse(
                bytes = Some(result),
                status = 500,
                headers = Map(),
                statusText = "INTERNAL_SERVER_ERROR",
                cookies = Seq()
              ).asInstanceOf[StandaloneWSResponse]
            }
        } else if (raiseException) {
          Future.failed(new RuntimeException("This is a simulated unhandled exception"))
        } else if (request.method == "POST") {
          loadFile("requests").flatMap { expectedContent =>
            request.body match {
              case InMemoryBody(actualContent) =>
                val actualJson = Json.parse(actualContent.utf8String)
                val expectedJson = Json.parse(expectedContent.utf8String)
                actualJson shouldBe expectedJson

                loadFile("responses").map { result =>
                  MockWSResponse(
                    bytes = Some(result),
                    status = 200,
                    headers = Map(),
                    statusText = "OK",
                    cookies = Seq()
                  ).asInstanceOf[StandaloneWSResponse]
                }
              case _ => fail("Unable to verify the request content")
            }

          }
        } else {
          loadFile("responses").map { result =>
            MockWSResponse(
              bytes = Some(result),
              status = 200,
              headers = Map(),
              statusText = "OK",
              cookies = Seq()
            ).asInstanceOf[StandaloneWSResponse]
          }
        }
      } else {
        fail(s"expected: https://mandrillapp.com/api/1.0$path, actual: ${request.uri}")
      }
    }

    f(MockWSRequest.MockWS(test = test))
  }

  import scala.concurrent.ExecutionContext.global
  implicit val ec: ExecutionContext = global

  override def afterAll(): Unit = {
    scamandrill.shutdown()
    system.terminate()
  }

}
