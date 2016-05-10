package io.github.scamandrill.models

import io.github.scamandrill.utils._

import scala.concurrent.duration._

object DefaultConfig {

  lazy val defaultKeyFromConfig: String = config.getString("Mandrill.key")
  lazy val defaultTimeout: FiniteDuration = config.getInt("Mandrill.timoutInSeconds").seconds

}
