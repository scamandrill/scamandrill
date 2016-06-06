package io.github.scamandrill.models

import io.github.scamandrill.MandrillSpec

class MandrillMessagesRequestsTest extends MandrillSpec {
  "MSendMsg" should "copy to an equal msendmessage" in {
    val msg = new MSendMsg(
      html = "<h1>HTML CONTENT</h1",
      text = "TEXT CONTENT",
      subject = "I'm a Subject",
      from_email = "bmcboatface@nerc.ac.uk",
      from_name = "Boaty McBoatface",
      to = List(MTo(
        email = "test@test.com",
        name = Some("Test Testerson")
      ))
    )

    msg shouldBe msg.copy()
  }
}
