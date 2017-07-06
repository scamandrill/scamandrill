package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
class ScamandrillTest extends MandrillSpec {
  "Scamandrill" should "use the key from application.conf if no config is specified" in {
    val instance = Scamandrill()
    try {
      instance.get().key.key shouldBe "example key"
    } finally {
      instance.shutdown()
    }
  }
}
