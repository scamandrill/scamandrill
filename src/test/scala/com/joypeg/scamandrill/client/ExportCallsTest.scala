package com.joypeg.scamandrill.client

import com.joypeg.scamandrill.MandrillSpec

import com.joypeg.scamandrill.models._
import com.joypeg.scamandrill.MandrillTestUtils._

class ExportCallsTest extends MandrillSpec {

//  "ExportList" should "work getting a valid MExportResponse (async client)" in {
//    val res = Await.result(MandrillAsyncClient.exportList(MKey()), DefaultConfig.defaultTimeout)
//    res shouldBe Nil
//  }
//  it should "work getting a valid  MExportResponse (blocking client)" in {
//    mandrillBlockingClient.exportList(MKey()) match {
//      case Success(res) =>
//        res shouldBe Nil
//      case Failure(ex) => fail(ex)
//    }
//  }
  "ExportList" should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.exportList(MKey(key="invalid")))
  }


  "ExportInfo" should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.exportInfo(MExportInfo(id = "exmaple", key="invalid")))
  }


//  "ExportWhitelist" should "work getting a valid MExportResponse (async client)" in {
//    val res = Await.result(MandrillAsyncClient.exportWhitelist(MExportNotify(notify_email = "example@example.com")), DefaultConfig.defaultTimeout)
//    res.`type` shouldBe "whitelist"
//  }
//  it should "work getting a valid  MExportResponse (blocking client)" in {
//    mandrillBlockingClient.exportWhitelist(MExportNotify(notify_email = "example@example.com")) match {
//      case Success(res) =>
//        res.`type` shouldBe "whitelist"
//      case Failure(ex) => fail(ex)
//    }
//  }
  "ExportWhitelist" should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.exportWhitelist(MExportNotify(notify_email = "example@example.com", key="invalid")))
  }

//  "ExportReject" should "work getting a valid MExportResponse (async client)" in {
//    val res = Await.result(MandrillAsyncClient.exportReject(MExportNotify(notify_email = "examplereject@example.com")), DefaultConfig.defaultTimeout)
//    res.`type` shouldBe "add"
//  }
//  it should "work getting a valid  MExportResponse (blocking client)" in {
//    mandrillBlockingClient.exportReject(MExportNotify(notify_email = "examplereject@example.com")) match {
//      case Success(res) =>
//        res.`type` shouldBe "add"
//      case Failure(ex) => fail(ex)
//    }
//  }
  "ExportReject" should "fail if the key passed is invalid, with an 'Invalid_Key' code" in {
    checkFailedBecauseOfInvalidKey(mandrillBlockingClient.exportReject(MExportNotify(notify_email = "examplereject@example.com", key="invalid")))
  }
}
