package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import play.api.Configuration
import play.api.libs.ws.ning.NingWSClient

class ScamandrillTest extends MandrillSpec {
  "Scamandrill" should "create a new WSClient using overridden values from application.Mandrill" in {
    val config = Configuration(
      "play.ws.ning.maxConnectionsPerHost" -> 5
    )
    val instance = Scamandrill(config)
    try {
      instance.ws match {
        case NingWSClient(underlying) => underlying.getMaxConnectionsPerHost shouldBe 5
        case _ => fail("The underlying client should be a NingWSClient if scamandrill is constructed with no args")
      }
    } finally {
      instance.shutdown()
    }
  }
}
