package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import play.api.Configuration
import play.api.libs.ws.ahc.AhcWSClient

class ScamandrillTest extends MandrillSpec {
  "Scamandrill" should "create a new WSClient using default config" in {
    val instance = Scamandrill()
    instance.ws match {
      case AhcWSClient(underlying) => underlying.getConnectTimeout shouldBe 121
      case _ => fail("The underlying client should be a AhcWSClient if scamandrill is constructed with no args")
    }
  }

  "Scamandrill" should "create a new WSClient using overridden values from application.Mandrill" in {
    val instance = Scamandrill(Configuration(
      "play.ws.ahc.maxConnectionsPerHost" -> 5
    ))
    instance.ws match {
      case AhcWSClient(underlying) => underlying.getMaxConnectionsPerHost shouldBe 5
      case _ => fail("The underlying client should be a AhcWSClient if scamandrill is constructed with no args")
    }
  }
}
