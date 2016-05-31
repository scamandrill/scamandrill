package io.github.scamandrill

import com.typesafe.config.ConfigFactory
import io.github.scamandrill.utils.SimpleLogger
import org.scalatest._
import org.scalatest.concurrent.PatienceConfiguration
import org.scalatest.time.{Seconds, Span}
import play.api.Configuration


trait MandrillSpec extends FlatSpec with Matchers with SimpleLogger with PatienceConfiguration {
  def defaultTimeout = {
    Configuration(ConfigFactory.load("application.conf")).getInt("mandrill.timoutInSeconds") match {
      case Some(t) => timeout(Span(t, Seconds))
      case None =>
        fail("Unable to load mandrill.timeoutInSeconds from application.conf")
    }
  }
}
