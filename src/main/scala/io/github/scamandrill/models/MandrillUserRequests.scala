package io.github.scamandrill.models

import play.api.libs.json.{JsObject, JsValue, Writes}

/**
  * A valid API key
  *
  */
case class MVoid() extends MandrillRequest
case object MVoid {
  implicit val writes = new Writes[MVoid] {
    override def writes(o: MVoid): JsValue = JsObject(Map[String, JsValue]())
  }
}
