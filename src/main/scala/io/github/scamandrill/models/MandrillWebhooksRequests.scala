package io.github.scamandrill.models

/**
  * The webhook info
  *
  * @param url         - The URL that the event data will be posted to
  * @param description a description of the webhook
  * @param events      - The message events that will be posted to the hook
  */
case class MWebhook(url: String,
                    description: String,
                    events: List[String]) extends MandrillRequest

/**
  * The webhook info
  *
  * @param id  - a unique integer indentifier for the webhook
  */
case class MWebhookInfo(id: Int) extends MandrillRequest

/**
  * The webhook to update
  *
  * @param id          - a unique integer indentifier for the webhook
  * @param url         - The URL that the event data will be posted to
  * @param description a description of the webhook
  * @param events      - The message events that will be posted to the hook
  */
case class MWebhookUpdate(id: Int,
                          url: String,
                          description: String,
                          events: List[String]) extends MandrillRequest


