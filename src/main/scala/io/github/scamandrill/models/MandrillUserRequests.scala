package io.github.scamandrill.models

import play.api.libs.json.{JsObject, JsValue, Writes}

/**
  * A valid API key
  *
  */
case class MVoid()
case object MVoid {
  implicit val writes: Writes[MVoid] = new Writes[MVoid] {
    override def writes(o: MVoid): JsValue = JsObject(Map[String, JsValue]())
  }
}
