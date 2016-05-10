package io.github.scamandrill.models

import scala.concurrent.duration._
import io.github.scamandrill.utils._

object DefaultConfig{

  lazy val defaultKeyFromConfig: String = config.getString("Mandrill.key")
  lazy val defaultTimeout: FiniteDuration = config.getInt("Mandrill.timoutInSeconds").seconds

}
