package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._

import scala.util.Success

class MetadataCallsTest extends MandrillSpec {

  "List" should "handle the example at https://www.mandrillapp.com/api/docs/metadata.JSON.html#method=list" in {
    withClient("/metadata/list.json") { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.metadataList(), defaultTimeout)(_ shouldBe Success(List(
        MIMetadataResponse(
          name = "group_id",
          state = "active",
          view_template = "<a href=\"http://yourapplication.com/user/{{value}}\">{{value}}</a>"
        )
      )))
    }
  }

  "Add" should "handle the example at https://www.mandrillapp.com/api/docs/metadata.JSON.html#method=add" in {
    withClient("/metadata/add.json") { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.metadataAdd(MMeteadatapAdd(
        name = "group_id",
        view_template= "<a href=\"http://yourapplication.com/user/{{value}}\">{{value}}</a>"
      )), defaultTimeout)(_ shouldBe Success(
        MIMetadataResponse(
          name = "group_id",
          state = "active",
          view_template = "<a href=\"http://yourapplication.com/user/{{value}}\">{{value}}</a>"
        )
      ))
    }
  }

  "Update" should "handle the example at https://www.mandrillapp.com/api/docs/metadata.JSON.html#method=update" in {
    withClient("/metadata/update.json") { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.metadataUpdate(MMeteadatapAdd(
        name = "group_id",
        view_template= "<a href=\"http://yourapplication.com/user/{{value}}\">{{value}}</a>"
      )), defaultTimeout)(_ shouldBe Success(
        MIMetadataResponse(
          name = "group_id",
          state = "active",
          view_template = "<a href=\"http://yourapplication.com/user/{{value}}\">{{value}}</a>"
        )
      ))
    }
  }

  "Delete" should "handle the example at https://www.mandrillapp.com/api/docs/metadata.JSON.html#method=delete" in {
    withClient("/metadata/delete.json") { wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.metadataDelete(MMeteadatapDelete(
        name = "group_id"
      )), defaultTimeout)(_ shouldBe Success(
        MIMetadataResponse(
          name = "group_id",
          state = "active",
          view_template = "<a href=\"http://yourapplication.com/user/{{value}}\">{{value}}</a>"
        )
      ))
    }
  }
}

