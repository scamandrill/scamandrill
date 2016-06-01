package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The webhook info
  *
  * @param url         - The URL that the event data will be posted to
  * @param description a description of the webhook
  * @param events      - The message events that will be posted to the hook
  */
case class MWebhook(url: String,
                    description: String,
                    events: List[String])
case object MWebhook {
  implicit val writes = Json.writes[MWebhook]
}

/**
  * The webhook info
  *
  * @param id  - a unique integer indentifier for the webhook
  */
case class MWebhookInfo(id: Int)
case object MWebhookInfo {
  implicit val writes = Json.writes[MWebhookInfo]
}

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
                          events: List[String])
case object MWebhookUpdate {
  implicit val writes = Json.writes[MWebhookUpdate]
}


