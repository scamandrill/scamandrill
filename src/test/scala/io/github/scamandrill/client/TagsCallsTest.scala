package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec

import scala.concurrent.Await
import io.github.scamandrill.models._

import io.github.scamandrill.MandrillTestUtils._
import io.github.scamandrill.models.MTagResponse

import scala.util.{Failure, Success, Try}

class TagsCallsTest extends MandrillSpec {

  "TagList" should "work getting a valid List[MTagResponse] (async client)" in {
    val res: List[MTagResponse] = Await.result(client.tagList(MKey()), DefaultConfig.defaultTimeout)
    val head: MTagResponse = res.head
    head.tag shouldBe "exampletag1"
  }

//  "TagDelete" should "work getting a valid MTagResponse (async client)" in {
//    val res = Await.result(client.tagDelete(MTagRequest(key = "twotag")), DefaultConfig.defaultTimeout)
//    res.getClass shouldBe classOf[MTagResponse]
//    res.key shouldBe "twotag"
//  }
//  it should "work getting a valid MTagResponse (blocking client)" in {
//    mandrillBlockingClient.tagDelete(MTagRequest(key = "twotag")) match {
//      case Success(res) =>
//        res.key shouldBe "twotag"
//      case Failure(ex) => fail(ex)
//    }
//  }

  "TagInfo" should "work getting a valid MTagInfoResponse (async client)" in {
    val res = Await.result(client.tagInfo(MTagRequest(tag = "exampletag1")), DefaultConfig.defaultTimeout)
    res.getClass shouldBe classOf[MTagInfoResponse]
    res.tag shouldBe "exampletag1"
  }

  "TagTimeSeries" should "work getting a valid List[MTimeSeriesResponse] (async client)" in {
    val res = Await.result(client.tagTimeSeries(MTagRequest(tag = "exampletag1")), DefaultConfig.defaultTimeout)
    //res shouldBe Nil
  }

  "TagAllTimeSeries" should "work getting a valid List[MTimeSeriesResponse] (async client)" in {
    val res = Await.result(client.tagAllTimeSeries(MKey()), DefaultConfig.defaultTimeout)
    //res shouldBe Nil
  }
}