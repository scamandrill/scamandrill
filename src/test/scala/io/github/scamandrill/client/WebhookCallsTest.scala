package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.concurrent.Await

class WebhookCallsTest extends MandrillSpec {
  "WebhookList" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=list" in {
    withClient("/webhooks/list.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.webhookList(), defaultTimeout)(_ shouldBe MandrillSuccess(List(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = Some(List(
          "send",
          "open",
          "click"
        )),
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      ))))
    }
  }

  "WebhookAdd" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=add" in {
    withClient("/webhooks/add.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.webhookAdd(MWebhook(
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        events = List(
          "send",
          "open",
          "click"
        )
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = Some(List(
          "send",
          "open",
          "click"
        )),
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }

  "WebhookInfo" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=info" in {
    withClient("/webhooks/info.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.webhookInfo(MWebhookInfo(
        id = 42
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = Some(List(
          "send",
          "open",
          "click"
        )),
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }

  "WebhookUpdate" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=update" in {
    withClient("/webhooks/update.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.webhookUpdate(MWebhookUpdate(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        events = List(
          "send",
          "open",
          "click"
        )
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = Some(List(
          "send",
          "open",
          "click"
        )),
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }

  "WebhookDelete" should "handle the example at https://www.mandrillapp.com/api/docs/webhooks.JSON.html#method=delete" in {
    withClient("/webhooks/delete.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.webhookDelete(MWebhookInfo(
        id = 42
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MWebhooksResponse(
        id = 42,
        url = "http://example/webhook-url",
        description = "My Example Webhook",
        auth_key = "gplJ8yWptFTqCoq5S1SHPA",
        events = Some(List(
          "send",
          "open",
          "click"
        )),
        created_at = "2013-01-01 15:30:27",
        last_sent_at = "2013-01-01 15:30:49",
        batches_sent = 42,
        events_sent = 42,
        last_error = "example last_error".?
      )))
    }
  }
}
