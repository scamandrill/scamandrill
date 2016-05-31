package io.github.scamandrill.client

import scala.util.{Failure, Success, Try}


sealed abstract class MandrillResponse[+T] {

  /**
    * @param default
    * @tparam U
    * @return the value of the MandrillResponse if successful or the default otherwise
    */
  def getOrElse[U >: T](default: => U): U = this match {
    case MandrillSuccess(value) => value
    case MandrillFailure(_) => default
  }

  /**
    *
    * @param default
    * @return A the currend response success if successful, the default otherwise
    */
  def orElse[U >: T](default: => MandrillSuccess[U]): MandrillSuccess[U] = this match {
    case r@MandrillSuccess(_) => r
    case MandrillFailure(_) => default
  }

  /**
    * @return the value of the MandrillResponse if successful, otherwise throws an exception
    */
  def get: T = this match {
    case MandrillSuccess(value) => value
    case MandrillFailure(ex) => throw ex
  }

  /**
    * @param f
    */
  def foreach[U](f: T => U): Unit = this match {
    case MandrillSuccess(value) => f(value)
    case MandrillFailure(_) => ()
  }

  def map[U](f: T => U): MandrillResponse[U] = this match {
    case MandrillSuccess(value) => MandrillSuccess(f(value))
    case MandrillFailure(error) => MandrillFailure[U](error)
  }

  def flatMap[U](f: T => MandrillResponse[U]): MandrillResponse[U] = this match {
    case MandrillSuccess(value) => f(value)
    case MandrillFailure(error) => MandrillFailure(error)
  }

  def filter(pred: T => Boolean): MandrillResponse[T] = this match {
    case s@MandrillSuccess(value) if pred(value) => s
    case MandrillSuccess(_) => MandrillFailure(new PredicateNotMatched)
    case f => f
  }

  def toOption(): Option[T] = this match {
    case MandrillSuccess(value) => Some(value)
    case MandrillFailure(_) => None
  }

  def asTry(): Try[T] = this match {
    case MandrillSuccess(value) => Success(value)
    case MandrillFailure(error) => Failure(error)
  }
}

final class PredicateNotMatched extends Exception
final case class MandrillSuccess[+T](value: T) extends MandrillResponse[T]
final case class MandrillFailure[+T](error: Exception) extends MandrillResponse[T]
