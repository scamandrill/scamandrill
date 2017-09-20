package io.github.scamandrill.models

import play.api.libs.json.{JsSuccess, Reads, _}

sealed trait JsScalar {
  def underlying: JsValue
}

object JsScalar {
  case class JsScalarString(s: String) extends JsScalar { override def underlying = JsString(s) }
  case class JsScalarNumber(n: BigDecimal) extends JsScalar { override def underlying = JsNumber(n) }
  case class JsScalarBoolean(b: Boolean) extends JsScalar { override def underlying = JsBoolean(b) }

  implicit val writes: Writes[JsScalar] = new Writes[JsScalar] {
    override def writes(o: JsScalar): JsValue = o.underlying
  }

  implicit val reads: Reads[JsScalar] = new Reads[JsScalar] {
    override def reads(json: JsValue): JsResult[JsScalar] = json match {
      case s: JsString => JsSuccess(JsScalarString(s.value))
      case b: JsBoolean => JsSuccess(JsScalarBoolean(b.value))
      case n: JsNumber => JsSuccess(JsScalarNumber(n.value))
      case _ => JsError("JsScalar must be a String, Boolean or Number")
    }
  }
}
