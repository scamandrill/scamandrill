package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.concurrent.Await

class WebhookCallsTest extends MandrillSpec {
  "WebhookList" should "work getting a valid List[MWebhooksResponse]" in {
    val res = Await.result(client.webhookList(MKey()), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }
}
