package com.joypeg.scamandrill

import akka.http.scaladsl.Http
import com.joypeg.scamandrill.client._
import com.joypeg.scamandrill.utils.SimpleLogger
import org.scalatest.{BeforeAndAfterEach, FlatSpec, Matchers, Retries, Suite}

import scala.concurrent.{Await, Future}
import scala.util.{Failure, Success, Try}
import scala.concurrent.duration._
import scala.language.postfixOps

trait MandrillBinder extends BeforeAndAfterEach { this: Suite =>
  var mandrillAsyncClient = new MandrillAsyncClient()
  var mandrillBlockingClient = new MandrillBlockingClient(mandrillAsyncClient.system)
  implicit var mat = mandrillAsyncClient.materializer
  implicit var ec = mandrillAsyncClient.system.dispatcher

  def shutdown(): Unit = {
    implicit val system = mandrillAsyncClient.system
    Await.ready(Http().shutdownAllConnectionPools(), Duration.Inf)
    Await.ready(mandrillAsyncClient.system.terminate(), Duration.Inf)
  }

  override def beforeEach(): Unit = {
    mandrillAsyncClient = new MandrillAsyncClient()
    mandrillBlockingClient = new MandrillBlockingClient(mandrillAsyncClient.system)
    mat = mandrillAsyncClient.materializer
    ec = mandrillAsyncClient.system.dispatcher
    super.beforeEach()
  }

  override def afterEach(): Unit = {
    try super.beforeEach()
    finally shutdown()
  }
}

trait MandrillSpec extends FlatSpec with MandrillBinder with Matchers with SimpleLogger with Retries {
  /**
    * A simple fuction to check equality of the errors from mandrill, but it also
    * display the reason of failure on screen, thing that I would loose using equality
    * on the errors themselves.
    * @param expected - the expected error
    * @param response - the error return from Mandrill
    */
  def checkError(expected: MandrillResponseException, responseF: Future[MandrillResponseException]): Unit = {
    val response = Await.result(responseF, 10 seconds)
    expected.httpCode shouldBe response.httpCode
    expected.httpReason shouldBe response.httpReason
    expected.mandrillError.code shouldBe response.mandrillError.code
    expected.mandrillError.message shouldBe response.mandrillError.message
    expected.mandrillError.name shouldBe response.mandrillError.name
    expected.mandrillError.status shouldBe response.mandrillError.status
  }

  /**
    * Utility method to check the failure because of an invalid key
    * @param response - the response from mandrill api
    */
  def checkFailedBecauseOfInvalidKey(response: Try[Any]): Unit = response match {
    case Success(res) =>
      fail("This operation should be unsuccessful")
    case Failure(ex: UnsuccessfulResponseException) =>
      val inernalError = MandrillError("error", -1, "Invalid_Key", "Invalid API key")
      val expected = new MandrillResponseException(500, "Internal Server Error", inernalError)
      checkError(expected, MandrillResponseException(ex))
    case Failure(ex) =>
      println(s"Failure is ${ex}")
      println(s"of class ${ex.getClass}")
      fail("should return an UnsuccessfulResponseException that can be parsed as MandrillResponseException")
  }

  override def withFixture(test: NoArgTest) = {
    if (isRetryable(test))
      withRetry { super.withFixture(test) }
    else
      super.withFixture(test)
  }
}