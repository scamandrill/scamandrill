package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

import scala.util.Success

class TemplateCallsTest extends MandrillSpec {

  "TemplateAdd" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=add" in {
    withClient("/templates/add.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templateAdd(MTemplate(
        name = "Example Template",
        from_email = "from_email@example.com",
        from_name = "Example Name",
        subject = "example subject",
        code = "<div>example code</div>",
        text = "Example text content",
        publish = false,
        labels = List(
          "example-label"
        )
      )), defaultTimeout)(_ shouldBe Success(MTemplateAddResponses(
        slug = "example-template",
        name = "Example Template",
        labels = List(
          "example-label"
        ),
        code = "<div mc:edit=\"editable\">editable content</div>",
        subject = "example subject",
        from_email = "from.email@example.com".?,
        from_name = "Example Name".?,
        text = "Example text",
        publish_name = "Example Template",
        publish_code = "<div mc:edit=\"editable\">different than draft content</div>".?,
        publish_subject = "example publish_subject".?,
        publish_from_email = "from.email.published@example.com".?,
        publish_from_name = "Example Published Name".?,
        publish_text = "Example published text".?,
        published_at = "2013-01-01 15:30:40".?,
        created_at = "2013-01-01 15:30:27",
        updated_at = "2013-01-01 15:30:49"
      )))
    }
  }

  "TemplatePublish" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=publish" in {
    withClient("/templates/publish.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templatePublish(MTemplateInfo(
        name = "Example Template"
      )), defaultTimeout)(_ shouldBe Success(MTemplateAddResponses(
        slug = "example-template",
        name = "Example Template",
        labels = List(
          "example-label"
        ),
        code = "<div mc:edit=\"editable\">editable content</div>",
        subject = "example subject",
        from_email = "from.email@example.com".?,
        from_name = "Example Name".?,
        text = "Example text",
        publish_name = "Example Template",
        publish_code = "<div mc:edit=\"editable\">different than draft content</div>".?,
        publish_subject = "example publish_subject".?,
        publish_from_email = "from.email.published@example.com".?,
        publish_from_name = "Example Published Name".?,
        publish_text = "Example published text".?,
        published_at = "2013-01-01 15:30:40".?,
        created_at = "2013-01-01 15:30:27",
        updated_at = "2013-01-01 15:30:49"
      )))
    }
  }

  "TemplateInfo" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=info" in {
    withClient("/templates/info.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templateInfo(MTemplateInfo(
        name = "Example Template"
      )), defaultTimeout)(_ shouldBe Success(MTemplateAddResponses(
        slug = "example-template",
        name = "Example Template",
        labels = List(
          "example-label"
        ),
        code = "<div mc:edit=\"editable\">editable content</div>",
        subject = "example subject",
        from_email = "from.email@example.com".?,
        from_name = "Example Name".?,
        text = "Example text",
        publish_name = "Example Template",
        publish_code = "<div mc:edit=\"editable\">different than draft content</div>".?,
        publish_subject = "example publish_subject".?,
        publish_from_email = "from.email.published@example.com".?,
        publish_from_name = "Example Published Name".?,
        publish_text = "Example published text".?,
        published_at = "2013-01-01 15:30:40".?,
        created_at = "2013-01-01 15:30:27",
        updated_at = "2013-01-01 15:30:49"
      )))
    }
  }

  "TemplateList" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=list" in {
    withClient("/templates/list.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templateList(MTemplateList(
        label = "example-label"
      )), defaultTimeout)(_ shouldBe Success(List(MTemplateAddResponses(
        slug = "example-template",
        name = "Example Template",
        labels = List(
          "example-label"
        ),
        code = "<div mc:edit=\"editable\">editable content</div>",
        subject = "example subject",
        from_email = "from.email@example.com".?,
        from_name = "Example Name".?,
        text = "Example text",
        publish_name = "Example Template",
        publish_code = "<div mc:edit=\"editable\">different than draft content</div>".?,
        publish_subject = "example publish_subject".?,
        publish_from_email = "from.email.published@example.com".?,
        publish_from_name = "Example Published Name".?,
        publish_text = "Example published text".?,
        published_at = "2013-01-01 15:30:40".?,
        created_at = "2013-01-01 15:30:27",
        updated_at = "2013-01-01 15:30:49"
      ))))
    }
  }

  "TemplateUpdate" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=update" in {
    withClient("/templates/update.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templateUpdate(MTemplate(
        name = "Example Template",
        from_email = "from_email@example.com",
        from_name = "Example Name",
        subject = "example subject",
        code = "<div>new example code</div>",
        text = "Example text content",
        publish = false,
        labels = List(
          "example-label"
        )
      )), defaultTimeout)(_ shouldBe Success(MTemplateAddResponses(
        slug = "example-template",
        name = "Example Template",
        labels = List(
          "example-label"
        ),
        code = "<div mc:edit=\"editable\">editable content</div>",
        subject = "example subject",
        from_email = "from.email@example.com".?,
        from_name = "Example Name".?,
        text = "Example text",
        publish_name = "Example Template",
        publish_code = "<div mc:edit=\"editable\">different than draft content</div>".?,
        publish_subject = "example publish_subject".?,
        publish_from_email = "from.email.published@example.com".?,
        publish_from_name = "Example Published Name".?,
        publish_text = "Example published text".?,
        published_at = "2013-01-01 15:30:40".?,
        created_at = "2013-01-01 15:30:27",
        updated_at = "2013-01-01 15:30:49"
      )))
    }
  }

  "TemplateTimeSeries" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=time-series" in {
    withClient("/templates/time-series.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templateTimeSeries(MTemplateInfo(
        name = "Example Template"
      )), defaultTimeout)(_ shouldBe Success(List(
        MTimeSeriesResponse(
          time = "2013-01-01 15:00:00",
          sent = 42,
          hard_bounces = 42,
          soft_bounces = 42,
          rejects = 42,
          complaints = 42,
          opens = 42,
          unique_opens = 42,
          clicks = 42,
          unique_clicks = 42
        )
      )))
    }
  }

  "TemplateRender" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=render" in {
    withClient("/templates/render.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templateRender(MTemplateRender(
        template_name = "Example Template",
        template_content = List(MTemplateCnt(
          name = "editable",
          content = "<div>content to inject *|MERGE1|*</div>"
        )),
        merge_vars = List(MTemplateCnt(
          name = "merge1",
          content = "merge1 content"
        ))
      )), defaultTimeout)(_ shouldBe Success(MTemplateRenderResponse(
        html = "<p><div>content to inject merge1 content</div></p>".?
      )))
    }
  }

  "TemplateDelete" should "handle the example at https://www.mandrillapp.com/api/docs/templates.JSON.html#method=delete" in {
    withClient("/templates/delete.json"){ wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.templateDelete(MTemplateInfo(
        name = "Example Template"
      )), defaultTimeout)(_ shouldBe Success(MTemplateAddResponses(
        slug = "example-template",
        name = "Example Template",
        labels = List(
          "example-label"
        ),
        code = "<div mc:edit=\"editable\">editable content</div>",
        subject = "example subject",
        from_email = "from.email@example.com".?,
        from_name = "Example Name".?,
        text = "Example text",
        publish_name = "Example Template",
        publish_code = "<div mc:edit=\"editable\">different than draft content</div>".?,
        publish_subject = "example publish_subject".?,
        publish_from_email = "from.email.published@example.com".?,
        publish_from_name = "Example Published Name".?,
        publish_text = "Example published text".?,
        published_at = "2013-01-01 15:30:40".?,
        created_at = "2013-01-01 15:30:27",
        updated_at = "2013-01-01 15:30:49"
      )))
    }
  }
}
