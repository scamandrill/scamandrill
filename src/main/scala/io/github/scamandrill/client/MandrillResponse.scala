package io.github.scamandrill.client

import scala.util.{Failure, Success, Try}


sealed abstract class MandrillResponse[+T] {

  /**
    * @param default
    * @tparam U
    * @return the value of the MandrillResponse if successful or the default otherwise
    */
  def getOrElse[U >: T](default: => U): U = this match {
    case ResponseSuccess(value) => value
    case ResponseFailure(_) => default
  }

  /**
    *
    * @param default
    * @return A the currend response success if successful, the default otherwise
    */
  def orElse[U >: T](default: => ResponseSuccess[U]): ResponseSuccess[U] = this match {
    case r@ResponseSuccess(_) => r
    case ResponseFailure(_) => default
  }

  /**
    * @return the value of the MandrillResponse if successful, otherwise throws an exception
    */
  def get: T = this match {
    case ResponseSuccess(value) => value
    case ResponseFailure(ex) => throw ex
  }

  /**
    * @param f
    */
  def foreach[U](f: T => U): Unit = this match {
    case ResponseSuccess(value) => f(value)
    case ResponseFailure(_) => ()
  }

  def map[U](f: T => U): MandrillResponse[U] = this match {
    case ResponseSuccess(value) => ResponseSuccess(f(value))
    case ResponseFailure(error) => ResponseFailure[U](error)
  }

  def flatMap[U](f: T => MandrillResponse[U]): MandrillResponse[U] = this match {
    case ResponseSuccess(value) => f(value)
    case ResponseFailure(error) => ResponseFailure(error)
  }

  def filter(pred: T => Boolean): MandrillResponse[T] = this match {
    case s@ResponseSuccess(value) if pred => s
    case ResponseSuccess(_) => ResponseFailure(new PredicateNotMatched)
    case f => f
  }

  def toOption(): Option[T] = this match {
    case ResponseSuccess(value) => Some(value)
    case ResponseFailure(_) => None
  }

  def asTry(): Try[T] = this match {
    case ResponseSuccess(value) => Success(value)
    case ResponseFailure(error) => Failure(error)
  }
}

final class PredicateNotMatched extends Exception
final case class ResponseSuccess[+T](value: T) extends MandrillResponse[T]
final case class ResponseFailure[+T](error: Exception) extends MandrillResponse[T]
