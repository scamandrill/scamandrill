package io.github.scamandrill.models

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models.JsScalar._
import play.api.libs.json.{JsSuccess, Json}

class JsScalarTest extends MandrillSpec{
  "JsScalar" should "read a string value from a json string" in {
    Json.parse("\"Hello world\"").validate[JsScalar] shouldBe JsSuccess(JsScalarString("Hello world"))
  }

  it should "read an integer from a json string" in {
    Json.parse("8675309").validate[JsScalar] shouldBe JsSuccess(JsScalarNumber(8675309))
  }

  it should "read a decimal from a json string" in {
    Json.parse("3.14159265").validate[JsScalar] shouldBe JsSuccess(JsScalarNumber(3.14159265))
  }

  "JsScalarBoolean" should "read true from a json string" in {
    Json.parse("true").validate[JsScalar] shouldBe JsSuccess(JsScalarBoolean(true))
  }

  it should "read false from a json string" in {
    Json.parse("false").validate[JsScalar] shouldBe JsSuccess(JsScalarBoolean(false))
  }

}
