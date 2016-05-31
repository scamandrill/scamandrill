package io.github.scamandrill

import io.github.scamandrill.client._
import io.github.scamandrill.utils.SimpleLogger
import org.scalatest._

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

trait MandrillSpec extends FlatSpec with Matchers with SimpleLogger with Retries {
//  /**
//    * Utility method to check the failure because of an invalid key
//    *
//    * @param response - the response from mandrill api
//    */
//  def checkFailedBecauseOfInvalidKey(response: Try[Any]): Unit = response match {
//    case Success(res) =>
//      fail("This operation should be unsuccessful")
//    case Failure(ex: UnsuccessfulResponseException) =>
//      val inernalError = MandrillError("error", -1, "Invalid_Key", "Invalid API key")
//      val expected = new MandrillResponseException(500, "Internal Server Error", inernalError)
//      checkError(expected, MandrillResponseException(ex))
//    case Failure(ex) =>
//      println(s"Failure is ${ex}")
//      println(s"of class ${ex.getClass}")
//      fail("should return an UnsuccessfulResponseException that can be parsed as MandrillResponseException")
//  }
//
//  /**
//    * A simple fuction to check equality of the errors from mandrill, but it also
//    * display the reason of failure on screen, thing that I would loose using equality
//    * on the errors themselves.
//    *
//    * @param expected - the expected error
//    * @param response - the error return from Mandrill
//    */
//  def checkError(expected: MandrillResponseException, response: MandrillResponseException): Unit = {
//    expected.httpCode shouldBe response.httpCode
//    expected.httpReason shouldBe response.httpReason
//    expected.mandrillError.code shouldBe response.mandrillError.code
//    expected.mandrillError.message shouldBe response.mandrillError.message
//    expected.mandrillError.name shouldBe response.mandrillError.name
//    expected.mandrillError.status shouldBe response.mandrillError.status
//  }

  override def withFixture(test: NoArgTest) = {
    if (isRetryable(test))
      withRetry {
        super.withFixture(test)
      }
    else
      super.withFixture(test)
  }
}
