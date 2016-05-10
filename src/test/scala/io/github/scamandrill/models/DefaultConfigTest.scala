package io.github.scamandrill.models

import io.github.scamandrill.MandrillSpec

import scala.concurrent.duration._

class DefaultConfigTest extends MandrillSpec {

  "DefaultConfig" should "read the defaut key from the configuration" in {
    DefaultConfig.defaultKeyFromConfig.getClass shouldBe classOf[String]
  }

  it should "read the defaut timeout duration from the configuration" in {
    DefaultConfig.defaultTimeout.getClass shouldBe classOf[FiniteDuration]
    DefaultConfig.defaultTimeout shouldBe 10.seconds
  }
}
