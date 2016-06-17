package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.client.implicits._
import io.github.scamandrill.models.JsScalar._
import io.github.scamandrill.models.{MMetadata, MMetadataEntry, Optional}

class MandrillClientTest extends MandrillSpec{
  "implicits" should "build an MMetadata from a map-like constructor" in {
    MMetadata(
      "string" -> "Hello World",
      "number" -> 123456,
      "boolean" -> false
    ) shouldBe new MMetadata(
      MMetadataEntry("string", JsScalarString("Hello World")),
      MMetadataEntry("number", JsScalarNumber(123456)),
      MMetadataEntry("boolean", JsScalarBoolean(false))
    )
  }

  it should "make a list optional" in {
    List("Something").? shouldBe Some(List("Something"))
  }

  it should "make a string optional" in {
    "Something".? shouldBe Some("Something")
  }

  it should "make a boolean optional" in {
    false.? shouldBe Some(false)
  }


  it should "make an Optional[T] type optional" in {
    case class Test(v: Int) extends Optional[Test]
    Test(1234).? shouldBe Some(Test(1234))
  }
}
