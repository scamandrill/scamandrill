package io.github.scamandrill.client

import io.github.scamandrill.MandrillSpec
import io.github.scamandrill.models._
import io.github.scamandrill.client.implicits._

class SendersCallsTest extends MandrillSpec {

  "SendersList" should "handle the example at https://mandrillapp.com/api/docs/senders.JSON.html#method=list" in {
    withClient("/senders/list.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.sendersList, defaultTimeout)(_ shouldBe MandrillSuccess(List(MSendersListResp(
        address = "sender.example@mandrillapp.com",
        created_at = "2013-01-01 15:30:27",
        sent = 42,
        hard_bounces = 42,
        soft_bounces = 42,
        rejects = 42,
        complaints = 42,
        unsubs = 42,
        opens = 42,
        clicks = 42,
        unique_opens = 42,
        unique_clicks = 42
      ))))
    }
  }

  "SendersDomains" should "handle the example at https://www.mandrillapp.com/api/docs/senders.JSON.html#method=domains" in {
    withClient("/senders/domains.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.sendersDomains(), defaultTimeout)(_ shouldBe MandrillSuccess(List(MSendersDomainResponses(
        domain = "example.com",
        created_at = "2013-01-01 15:30:27".?,
        last_tested_at = "2013-01-01 15:40:42",
        spf = MSendersDom(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        dkim = MSendersDom(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        verified_at = "2013-01-01 15:50:21".?,
        valid_signing = true
      ))))
    }
  }

  "SendersAddDomain" should "handle the example at https://www.mandrillapp.com/api/docs/senders.JSON.html#method=add-domain" in {
    withClient("/senders/add-domain.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.sendersAddDomain(MSenderDomain(
        domain = "example.com"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSendersDomainResponses(
        domain = "example.com",
        created_at = "2013-01-01 15:30:27".?,
        last_tested_at = "2013-01-01 15:40:42",
        spf = MSendersDom(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        dkim = MSendersDom(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        verified_at = "2013-01-01 15:50:21".?,
        valid_signing = true
      )))
    }
  }

  "SendersCheckDomain" should "handle the example at https://www.mandrillapp.com/api/docs/senders.JSON.html#method=check-domain" in {
    withClient("/senders/check-domain.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.sendersCheckDomain(MSenderDomain(
        domain = "example.org"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSendersDomainResponses(
        domain = "example.com",
        created_at = "2013-01-01 15:30:27".?,
        last_tested_at = "2013-01-01 15:40:42",
        spf = MSendersDom(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        dkim = MSendersDom(
          valid = true,
          valid_after = "2013-01-01 15:45:23".?,
          error = "example error".?
        ),
        verified_at = "2013-01-01 15:50:21".?,
        valid_signing = true
      )))
    }
  }

  "SendersVerifyDomain" should "handle the example at https://www.mandrillapp.com/api/docs/senders.JSON.html#method=verify-domain" in {
    withClient("/senders/verify-domain.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.sendersVerifyDomain(MSenderVerifyDomain(
        domain = "example.com",
        mailbox = "your.name"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSendersVerifyDomResp(
        status = "example status",
        domain = "example domain",
        email = "example email"
      )))
    }
  }

  "SendersInfo" should "handle the example at https://www.mandrillapp.com/api/docs/senders.JSON.html#method=info" in {
    withClient("/senders/info.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.sendersInfo(MSenderAddress(
        address = "sender.example@mandrillapp.com"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(MSendersInfoResp(
        address = "sender.example@mandrillapp.com",
        created_at = "2013-01-01 15:30:27",
        sent = 42,
        hard_bounces = 42,
        soft_bounces = 42,
        rejects = 42,
        complaints = 42,
        unsubs = 42,
        opens = 42,
        clicks = 42,
        stats = MSendersStats(
          today = MStat(
            sent = 42,
            hard_bounces = 42,
            soft_bounces = 42,
            rejects = 42,
            complaints = 42,
            unsubs = 42,
            opens = 42,
            unique_opens = 42,
            clicks = 42,
            unique_clicks = 42
          ),
          last_7_days = MStat(
            sent = 42,
            hard_bounces = 42,
            soft_bounces = 42,
            rejects = 42,
            complaints = 42,
            unsubs = 42,
            opens = 42,
            unique_opens = 42,
            clicks = 42,
            unique_clicks = 42
          ),
          last_30_days = MStat(
            sent = 42,
            hard_bounces = 42,
            soft_bounces = 42,
            rejects = 42,
            complaints = 42,
            unsubs = 42,
            opens = 42,
            unique_opens = 42,
            clicks = 42,
            unique_clicks = 42
          ),
          last_60_days = MStat(
            sent = 42,
            hard_bounces = 42,
            soft_bounces = 42,
            rejects = 42,
            complaints = 42,
            unsubs = 42,
            opens = 42,
            unique_opens = 42,
            clicks = 42,
            unique_clicks = 42
          ),
          last_90_days = MStat(
            sent = 42,
            hard_bounces = 42,
            soft_bounces = 42,
            rejects = 42,
            complaints = 42,
            unsubs = 42,
            opens = 42,
            unique_opens = 42,
            clicks = 42,
            unique_clicks = 42
          )
        )
      )))
    }
  }

  "SendersTimeSeries" should "handle the example at https://www.mandrillapp.com/api/docs/senders.JSON.html#method=time-series" in {
    withClient("/senders/time-series.json"){ wc =>
      val instance = new MandrillClient(wc, new APIKey())
      whenReady(instance.sendersTimeSeries(MSenderAddress(
        address = "sender.example@mandrillapp.com"
      )), defaultTimeout)(_ shouldBe MandrillSuccess(List(MSenderTSResponse(
        unique_clicks = 42,
        time = "2013-01-01 15:30:27",
        sent = 42,
        hard_bounces = 42,
        soft_bounces = 42,
        rejects = 42,
        complaints = 42,
        opens = 42,
        unique_opens = 42,
        clicks = 42
      ))))
    }
  }
}
