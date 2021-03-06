package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import play.api.Configuration
import play.api.libs.ws.ahc.AhcWSClient

class ScamandrillTest extends MandrillSpec {
  "Scamandrill" should "create a new WSClient using overridden values from supplied config" in {
    val instance = Scamandrill(Configuration(
      "play.ws.ahc.maxConnectionsPerHost" -> 5
    ))
    try {
      instance.ws match {
        case AhcWSClient(underlying) => underlying.getMaxConnectionsPerHost shouldBe 5
        case _ => fail("The underlying client should be a AhcWSClient if scamandrill is constructed with no args")
      }
    } finally {
      instance.shutdown()
    }
  }

  it should "use the key from application.conf if no config is specified" in {
    val instance = Scamandrill()
    try {
      instance.get().key.key shouldBe "example key"
    } finally {
      instance.shutdown()
    }
  }
}
