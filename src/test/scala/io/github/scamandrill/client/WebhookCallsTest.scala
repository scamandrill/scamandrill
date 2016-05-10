package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._
import io.github.scamandrill.MandrillTestUtils._

import scala.util.Failure
import scala.util.Success

import org.scalatest.tagobjects.Retryable

class WebhookCallsTest extends MandrillSpec {
  "WebhookList" should "work getting a valid List[MWebhooksResponse]" in {
    val res = Await.result(client.webhookList(MKey()), DefaultConfig.defaultTimeout)
    res shouldBe Nil
  }
}
