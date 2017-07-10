package io.github.scamandrill.models

import play.api.libs.json.{JsObject, JsValue, Writes}

/**
  * A valid API key
  *
  */
case class MVoid()
case object MVoid {
  implicit val writes: Writes[MVoid] = (_: MVoid) => JsObject(Map[String, JsValue]())
}
