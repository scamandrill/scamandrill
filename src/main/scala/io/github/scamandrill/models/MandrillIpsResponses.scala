package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * Information about the ip's custom dns, if it has been configured
  *
  * @param enabled - a boolean indicating whether custom dns has been configured for this ip
  * @param valid   - whether the ip's custom dns is currently valid
  * @param error   - if the ip's custom dns is invalid, this will include details about the error
  */
case class MIpsDnsResp(enabled: Boolean,
                       valid: Boolean,
                       error: String)
case object MIpsDnsResp {
  implicit val reads = Json.reads[MIpsDnsResp]
}

/**
  * Information about the ip's warmup status
  *
  * @param warming_up - whether the ip is currently in warmup mode
  * @param start_at   - the start time for the warmup process as a UTC string in YYYY-MM-DD HH:MM:SS format
  * @param end_at     - the end date and time for the warmup process as a UTC string in YYYY-MM-DD HH:MM:SS format
  */
case class MIpsWarmupResp(warming_up: Boolean,
                          start_at: String,
                          end_at: String)
case object MIpsWarmupResp {
  implicit val reads = Json.reads[MIpsWarmupResp]
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
case class MIpsResponse(ip: String,
                        created_at: String,
                        pool: String,
                        domain: String,
                        custom_dns: MIpsDnsResp,
                        warmup: MIpsWarmupResp)
case object MIpsResponse {
  implicit val reads = Json.reads[MIpsResponse]
}

/**
  * A description of the provisioning request that was created
  *
  * @param requested_at - the date and time that the request was created as a UTC timestamp in YYYY-MM-DD HH:MM:SS format
  */
case class MIpsProvisionResp(requested_at: String)
case object MIpsProvisionResp {
  implicit val reads = Json.reads[MIpsProvisionResp]
}
/**
  * The ips to delete
  *
  * @param ip      - the ip address
  * @param deleted - a boolean indicating whether the ip was successfully deleted
  */
case class MIpsDelete(ip: String, deleted: Boolean)
case object MIpsDelete {
  implicit val reads = Json.reads[MIpsDelete]
}

/**
  * The pool information
  *
  * @param name       - this pool's name
  * @param created_at - the date and time that this pool was created as a UTC timestamp in YYYY-MM-DD HH:MM:SS format
  * @param ips        - the dedicated IPs in this pool
  */
case class MIpsInfoPoolResponse(name: String,
                        created_at: String,
                        ips: List[MIpsResponse])
case object MIpsInfoPoolResponse {
  implicit val reads = Json.reads[MIpsInfoPoolResponse]
}

/**
  * Validation results for the domain
  *
  * @param valid - whether the domain name has a correctly-configured A record pointing to the ip address
  * @param error - if valid is false, this will contain details about why the domain's A record is incorrect
  */
case class MIpsDnsResponse(valid: Boolean, error: String)
case object MIpsDnsResponse {
  implicit val reads = Json.reads[MIpsDnsResponse]
}

/**
  * Information about the status of the pool that was deleted
  *
  * @param pool    - the name of the pool
  * @param deleted - whether the pool was deleted
  */
case class MIpsDeletePoolResponse(pool: String, deleted: Boolean)
case object MIpsDeletePoolResponse {
  implicit val reads = Json.reads[MIpsDeletePoolResponse]
}
