package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.Success

class WebhookCallsTest extends MandrillSpec {
  "WebhookList" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=list" in {
    withMockClient("/webhooks/list.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.webhookList(), defaultTimeout)(_ shouldBe Success(List(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = List(
          "send",
          "open",
          "click"
        ).?,
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      ))))
    }
  }

  "WebhookAdd" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=add" in {
    withMockClient("/webhooks/add.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.webhookAdd(MWebhook(
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        events = List(
          "send",
          "open",
          "click"
        )
      )), defaultTimeout)(_ shouldBe Success(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = List(
          "send",
          "open",
          "click"
        ).?,
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }

  "WebhookInfo" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=info" in {
    withMockClient("/webhooks/info.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.webhookInfo(MWebhookInfo(
        id = 42
      )), defaultTimeout)(_ shouldBe Success(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = List(
          "send",
          "open",
          "click"
        ).?,
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }

  "WebhookUpdate" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=update" in {
    withMockClient("/webhooks/update.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.webhookUpdate(MWebhookUpdate(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        events = List(
          "send",
          "open",
          "click"
        )
      )), defaultTimeout)(_ shouldBe Success(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = List(
          "send",
          "open",
          "click"
        ).?,
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }

  "WebhookDelete" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=delete" in {
    withMockClient("/webhooks/delete.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.webhookDelete(MWebhookInfo(
        id = 42
      )), defaultTimeout)(_ shouldBe Success(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = List(
          "send",
          "open",
          "click"
        ).?,
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }
}
