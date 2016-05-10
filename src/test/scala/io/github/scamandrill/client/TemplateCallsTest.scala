package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._
import io.github.scamandrill.MandrillTestUtils._

import scala.util.{Failure, Success, Try}
import org.scalatest.tagobjects.Retryable

class TemplateCallsTest extends MandrillSpec {

  "TemplateAdd" should "work getting a valid MTemplateAddResponses (async client)" taggedAs(Retryable) in {
    val res: MTemplateAddResponses = Await.result(
      mandrillAsyncClient.templateAdd(validNonPublidhedTemplate2), DefaultConfig.defaultTimeout
    )
    res.publish_code shouldBe None
    res.slug shouldBe "templatetest2"
    res.publish_name shouldBe  "templatetest2"
  }

  "TemplatePublish" should "work getting a valid MTemplateAddResponses (async client)" in {
    val res = Await.result(mandrillAsyncClient.templatePublish(MTemplateInfo(name = validNonPublidhedTemplate.name)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MTemplateAddResponses]
    res.publish_code shouldBe Some("<div>example code</div>")
    res.slug shouldBe "templatetest"
    res.publish_name shouldBe  "templatetest"
  }

  "TemplateInfo" should "work getting a valid MTemplateAddResponses (async client)" taggedAs(Retryable) in {
    val res = Await.result(mandrillAsyncClient.templateInfo(MTemplateInfo(name = validNonPublidhedTemplate.name)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MTemplateAddResponses]
    res.publish_code shouldBe Some("<div>example code</div>")
    res.slug shouldBe "templatetest"
    res.publish_name shouldBe  "templatetest"
  }

  "TemplateList" should "work getting a valid List[MTemplateAddResponses] (async client)" in {
    val res = Await.result(mandrillAsyncClient.templateList(MTemplateList(label = validNonPublidhedTemplate.labels.head)), DefaultConfig.defaultTimeout)
    res.head.getClass shouldBe classOf[MTemplateAddResponses]
    res.head.publish_code shouldBe Some("<div>example code</div>")
    res.head.slug shouldBe "templatetest"
    res.head.publish_name shouldBe  "templatetest"
  }


  "TemplateUpdate" should "work getting a valid MTemplateAddResponses (async client)" in {
    val res = Await.result(mandrillAsyncClient.templateUpdate(validNonPublidhedTemplate2), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MTemplateAddResponses]
    res.publish_code shouldBe Some("<div>example code</div>")
    res.slug shouldBe "templatetest2"
    res.publish_name shouldBe  "templatetest2"
  }

//  "templateTimeSeries" should "work getting a valid List[MTimeSeriesResponse] (async client)" in {
//    val res = Await.result(mandrillAsyncClient.templateTimeSeries(MTemplateInfo(name = validNonPublidhedTemplate2.name)), DefaultConfig.defaultTimeout)
//    res.head.getClass shouldBe classOf[MTimeSeriesResponse]
//  }
//  it should "work getting a valid List[MTimeSeriesResponse] (blocking client)" in {
//    mandrillBlockingClient.templateTimeSeries(MTemplateInfo(name = validNonPublidhedTemplate.name)) match {
//      case Success(res) =>
//        res.head.getClass shouldBe classOf[MTimeSeriesResponse]
//      case Failure(ex) => fail(ex)
//    }
//  }

  "TemplateRender" should "work getting a valid MTemplateRenderResponse (async client)" in {
    val res = Await.result(mandrillAsyncClient.templateRender(validTemplateRender), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MTemplateRenderResponse]
    res.html shouldBe Some("<div>example code</div>")
  }

  "TemplateDelete" should "work getting a valid MTemplateAddResponses (async client)" in {
    val res = Await.result(mandrillAsyncClient.templateDelete(MTemplateInfo(name = validNonPublidhedTemplate2.name)), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MTemplateAddResponses]
  }
}
