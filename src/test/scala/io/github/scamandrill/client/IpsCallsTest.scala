package io.github.scamandrill.client

import io.github.scamandrill.{ActualAPICall, MandrillSpec}
import io.github.scamandrill.models._

import scala.util.{Failure, Success}

class IpsCallsTest extends MandrillSpec {

  "List" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=list" in {
    withMockClient("/ips/list.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsList(), defaultTimeout)(_ shouldBe Success(List(
        MIpsResponse(
          ip = "127.0.0.1",
          created_at = "2013-01-01 15:50:21",
          pool = "Main Pool",
          domain = "mail1.example.mandrillapp.com",
          custom_dns = MIpsDnsResp(
            enabled = true,
            valid = true,
            error = "example error"
          ),
          warmup = MIpsWarmupResp(
            warming_up = true,
            start_at = "2013-03-01 12:00:01",
            end_at = "2013-03-31 12:00:01"
          )
        )
      )))
    }
  }

  "Info" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=info" in {
    withMockClient("/ips/info.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsInfo(MIpsIp(
        ip = "example ip"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsResponse(
          ip = "127.0.0.1",
          created_at = "2013-01-01 15:50:21",
          pool = "Main Pool",
          domain = "mail1.example.mandrillapp.com",
          custom_dns = MIpsDnsResp(
            enabled = true,
            valid = true,
            error = "example error"
          ),
          warmup = MIpsWarmupResp(
            warming_up = true,
            start_at = "2013-03-01 12:00:01",
            end_at = "2013-03-31 12:00:01"
          )
        )
      ))
    }
  }

  "Provision" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=provision" in {
    withMockClient("/ips/provision.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsProvision(MIpsPool(
        warmup = true,
        pool = "Main Pool"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsProvisionResp(
          requested_at = "2013-01-01 01:52:21"
        )
      ))
    }
  }

  "Start-warmup" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=start-warmup" in {
    withMockClient("/ips/start-warmup.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsStartWarmup(MIpsIp(
        ip = "127.0.0.1"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsResponse(
          ip = "127.0.0.1",
          created_at = "2013-01-01 15:50:21",
          pool = "Main Pool",
          domain = "mail1.example.mandrillapp.com",
          custom_dns = MIpsDnsResp(
            enabled = true,
            valid = true,
            error = "example error"
          ),
          warmup = MIpsWarmupResp(
            warming_up = true,
            start_at = "2013-03-01 12:00:01",
            end_at = "2013-03-31 12:00:01"
          )
        )
      ))
    }
  }

  "Cancel-warmup" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=cancel-warmup" in {
    withMockClient("/ips/cancel-warmup.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsCancelWarmup(MIpsIp(
        ip = "127.0.0.1"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsResponse(
          ip = "127.0.0.1",
          created_at = "2013-01-01 15:50:21",
          pool = "Main Pool",
          domain = "mail1.example.mandrillapp.com",
          custom_dns = MIpsDnsResp(
            enabled = true,
            valid = true,
            error = "example error"
          ),
          warmup = MIpsWarmupResp(
            warming_up = true,
            start_at = "2013-03-01 12:00:01",
            end_at = "2013-03-31 12:00:01"
          )
        )
      ))
    }
  }

  "Set-pool" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=set-pool" in {
    withMockClient("/ips/set-pool.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsSetPool(MIpsSetPool(
        ip = "127.0.0.1",
        pool = "Main Pool",
        create_pool = true
      )), defaultTimeout)(_ shouldBe Success(
        MIpsResponse(
          ip = "127.0.0.1",
          created_at = "2013-01-01 15:50:21",
          pool = "Main Pool",
          domain = "mail1.example.mandrillapp.com",
          custom_dns = MIpsDnsResp(
            enabled = true,
            valid = true,
            error = "example error"
          ),
          warmup = MIpsWarmupResp(
            warming_up = true,
            start_at = "2013-03-01 12:00:01",
            end_at = "2013-03-31 12:00:01"
          )
        )
      ))
    }
  }

  "Delete" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=delete" in {
    withMockClient("/ips/delete.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsDelete(MIpsIp(
        ip = "127.0.0.1"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsDelete(
          ip = "127.0.0.1",
          deleted = true
        )
      ))
    }
  }

  "List-pools" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=list-pools" in {
    withMockClient("/ips/list-pools.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsListPool(), defaultTimeout)(_ shouldBe Success(List(
        MIpsInfoPoolResponse(
          name = "Main Pool",
          created_at = "2013-01-01 12:15:12",
          ips = List(MIpsResponse(
            ip = "127.0.0.1",
            created_at = "2013-01-01 15:50:21",
            pool = "Main Pool",
            domain = "mail1.example.mandrillapp.com",
            custom_dns = MIpsDnsResp(
              enabled = true,
              valid = true,
              error = "example error"
            ),
            warmup = MIpsWarmupResp(
              warming_up = true,
              start_at = "2013-03-01 12:00:01",
              end_at = "2013-03-31 12:00:01"
            )
          ))
        )
      )))
    }
  }

  "Pool-info" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=pool-info" in {
    withMockClient("/ips/pool-info.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsPoolInfo(MIpsPoolInfo(
        pool = "Main Pool"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsInfoPoolResponse(
          name = "Main Pool",
          created_at = "2013-01-01 12:15:12",
          ips = List(MIpsResponse(
            ip = "127.0.0.1",
            created_at = "2013-01-01 15:50:21",
            pool = "Main Pool",
            domain = "mail1.example.mandrillapp.com",
            custom_dns = MIpsDnsResp(
              enabled = true,
              valid = true,
              error = "example error"
            ),
            warmup = MIpsWarmupResp(
              warming_up = true,
              start_at = "2013-03-01 12:00:01",
              end_at = "2013-03-31 12:00:01"
            )
          ))
        )
      ))
    }
  }

  "Create-pool" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=create-pool" in {
    withMockClient("/ips/create-pool.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsCreatePool(MIpsPoolInfo(
        pool = "New Pool"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsInfoPoolResponse(
          name = "Main Pool",
          created_at = "2013-01-01 12:15:12",
          ips = List(MIpsResponse(
            ip = "127.0.0.1",
            created_at = "2013-01-01 15:50:21",
            pool = "Main Pool",
            domain = "mail1.example.mandrillapp.com",
            custom_dns = MIpsDnsResp(
              enabled = true,
              valid = true,
              error = "example error"
            ),
            warmup = MIpsWarmupResp(
              warming_up = true,
              start_at = "2013-03-01 12:00:01",
              end_at = "2013-03-31 12:00:01"
            )
          ))
        )
      ))
    }
  }

  "Delete-pool" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=delete-pool" in {
    withMockClient("/ips/delete-pool.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsDeletePool(MIpsPoolInfo(
        pool = "Delete Pool"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsDeletePoolResponse(
          pool = "Delete Pool",
          deleted = true
        )
      ))
    }
  }

  "Check-custom-dns" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=check-custom-dns" in {
    withMockClient("/ips/check-custom-dns.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsCheckCustomDns(MIpsDns(
        ip = "127.0.0.1",
        domain = "mail1.example.mandrillapp.com"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsDnsResponse(
          valid = true,
          error = "mail128.example.mandrillapp.com does not exist"
        )
      ))
    }
  }

  "Set-custom-dns" should "handle the example at https://www.mandrillapp.com/api/docs/ips.JSON.html#method=set-custom-dns" in {
    withMockClient("/ips/set-custom-dns.json") { wc =>
      val instance = new MandrillClient(wc)
      whenReady(instance.ipsSetCustomDns(MIpsDns(
        ip = "127.0.0.1",
        domain = "example domain"
      )), defaultTimeout)(_ shouldBe Success(
        MIpsResponse(
          ip = "127.0.0.1",
          created_at = "2013-01-01 15:50:21",
          pool = "Main Pool",
          domain = "mail1.example.mandrillapp.com",
          custom_dns = MIpsDnsResp(
            enabled = true,
            valid = true,
            error = "example error"
          ),
          warmup = MIpsWarmupResp(
            warming_up = true,
            start_at = "2013-03-01 12:00:01",
            end_at = "2013-03-31 12:00:01"
          )
        )
      ))
    }
  }

  "Actual IPs Calls" should "get an empty list before adding any IPs" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.ipsList(), defaultTimeout)(_ shouldBe Success(Nil))
    }
  }

  it should "get a valid pool on create" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.ipsCreatePool(MIpsPoolInfo(pool = "test")), defaultTimeout) {
        case Success(res) => res.name shouldBe "test"
        case Failure(t) => fail(t)
      }
    }
  }

  it should "get a list containing the test pool" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.ipsListPool(), defaultTimeout) {
        case Success(res) => res.headOption match {
          case Some(pool) => pool.name shouldBe "test"
          case None => fail("Expected a non-empty list of IP pools")
        }
        case Failure(t) => fail(t)
      }
    }
  }

  it should "retrieve the pool info" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.ipsPoolInfo(MIpsPoolInfo(pool = "test")), defaultTimeout) {
        case Success(res) => res.name shouldBe "test"
        case Failure(t) => fail(t)
      }
    }
  }

  it should "successfully delete the pool" taggedAs ActualAPICall in {
    assume(actualClient.isDefined)
    actualClient.foreach { client =>
      whenReady(client.ipsDeletePool(MIpsPoolInfo(pool = "test")), defaultTimeout)(_ shouldBe Success(MIpsDeletePoolResponse(pool = "test", deleted = true)))
    }
  }

}
