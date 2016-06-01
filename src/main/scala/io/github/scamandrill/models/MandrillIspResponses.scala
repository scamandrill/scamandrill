package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * Information about the ip's custom dns, if it has been configured
  *
  * @param enabled - a boolean indicating whether custom dns has been configured for this ip
  * @param valid   - whether the ip's custom dns is currently valid
  * @param error   - if the ip's custom dns is invalid, this will include details about the error
  */
case class MIspDnsResp(enabled: Boolean,
                       valid: Boolean,
                       error: String)
case object MIspDnsResp {
  implicit val reads = Json.reads[MIspDnsResp]
}

/**
  * Information about the ip's warmup status
  *
  * @param warming_up - whether the ip is currently in warmup mode
  * @param start_at   - the start time for the warmup process as a UTC string in YYYY-MM-DD HH:MM:SS format
  * @param end_at     - the end date and time for the warmup process as a UTC string in YYYY-MM-DD HH:MM:SS format
  */
case class MIspWarmupResp(warming_up: Boolean,
                          start_at: String,
                          end_at: String)
case object MIspWarmupResp {
  implicit val reads = Json.reads[MIspWarmupResp]
}

/**
  * Isp response
  *
  * @param ip         - the ip address
  * @param created_at - the date and time that the dedicated IP was created as a UTC string in YYYY-MM-DD HH:MM:SS format
  * @param pool       - the name of the pool that this dedicated IP belongs to
  * @param domain     - the domain name (reverse dns) of this dedicated IP
  * @param custom_dns - information about the ip's custom dns, if it has been configured
  * @param warmup     - information about the ip's warmup status
  */
case class MIspResponse(ip: String,
                        created_at: String,
                        pool: String,
                        domain: String,
                        custom_dns: MIspDnsResp,
                        warmup: MIspWarmupResp) extends MandrillResponse
case object MIspResponse {
  implicit val reads = Json.reads[MIspResponse]
}

/**
  * A description of the provisioning request that was created
  *
  * @param requested_at - the date and time that the request was created as a UTC timestamp in YYYY-MM-DD HH:MM:SS format
  */
case class MIspProvisionResp(requested_at: String) extends MandrillResponse
case object MIspProvisionResp {
  implicit val reads = Json.reads[MIspProvisionResp]
}
/**
  * The isp to delete
  *
  * @param ip      - the ip address
  * @param deleted - a boolean indicating whether the ip was successfully deleted
  */
case class MIspDelete(ip: String, deleted: Boolean) extends MandrillResponse
case object MIspDelete {
  implicit val reads = Json.reads[MIspDelete]
}

/**
  * The pool information
  *
  * @param name       - this pool's name
  * @param created_at - the date and time that this pool was created as a UTC timestamp in YYYY-MM-DD HH:MM:SS format
  * @param ips        - the dedicated IPs in this pool
  */
case class MIspInfoPool(name: String,
                        created_at: String,
                        ips: List[MIspResponse]) extends MandrillResponse
case object MIspInfoPool {
  implicit val reads = Json.reads[MIspInfoPool]
}

/**
  * Validation results for the domain
  *
  * @param valid - whether the domain name has a correctly-configured A record pointing to the ip address
  * @param error - if valid is false, this will contain details about why the domain's A record is incorrect
  */
case class MIspDnsResponse(valid: Boolean, error: String) extends MandrillResponse
case object MIspDnsResponse {
  implicit val reads = Json.reads[MIspDnsResponse]
}

/**
  * Information about the status of the pool that was deleted
  *
  * @param pool    - the name of the pool
  * @param deleted - whether the pool was deleted
  */
case class MIspDeletePoolResponse(pool: String, deleted: Boolean) extends MandrillResponse
case object MIspDeletePoolResponse {
  implicit val reads = Json.reads[MIspDeletePoolResponse]
}
