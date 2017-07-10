package io.github.scamandrill.models

import play.api.libs.json.{JsSuccess, Reads, _}

sealed trait JsScalar {
  def underlying: JsValue
}

object JsScalar {
  case class JsScalarString(s: String) extends JsScalar { override def underlying = JsString(s) }
  case class JsScalarNumber(n: BigDecimal) extends JsScalar { override def underlying = JsNumber(n) }
  case class JsScalarBoolean(b: Boolean) extends JsScalar { override def underlying = JsBoolean(b) }

  implicit val writes: Writes[JsScalar] = (o: JsScalar) => o.underlying

  implicit val reads: Reads[JsScalar] = {
    case s: JsString => JsSuccess(JsScalarString(s.value))
    case b: JsBoolean => JsSuccess(JsScalarBoolean(b.value))
    case n: JsNumber => JsSuccess(JsScalarNumber(n.value))
    case _ => JsError("JsScalar must be a String, Boolean or Number")
  }
}
