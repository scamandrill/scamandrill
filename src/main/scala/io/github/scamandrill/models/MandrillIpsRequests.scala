package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The Isp
  *
  * @param id  - th unique identifier
  */
case class MIpsId(id: String)

case object MIpsId {
  implicit val writes = Json.writes[MIpsId]
}
/**
  * The Isp
  *
  * @param ip  - a dedicated IP address
  */
case class MIpsIp(ip: String)
case object MIpsIp {
  implicit val writes = Json.writes[MIpsIp]
}

/**
  * The Isp pool
  *
  * @param warmup - whether the ip is currently in warmup mode
  * @param pool   - the name of the new pool to add the dedicated ip to
  */
case class MIpsPool(warmup: Boolean,
                    pool: String)
case object MIpsPool {
  implicit val writes = Json.writes[MIpsPool]
}

/**
  * The Ip pool info
  *
  * @param pool - the name of the new pool to add the dedicated ip to
  */
case class MIpsPoolInfo(pool: String)
case object MIpsPoolInfo {
  implicit val writes = Json.writes[MIpsPoolInfo]
}

/**
  * The Ip pool info
  *
  * @param ip          - a dedicated IP address
  * @param pool        - the name of the new pool to add the dedicated ip to
  * @param create_pool - whether to create the pool if it does not exist; if false and the pool does not exist, an Unknown_Pool will be thrown.
  */
case class MIpsSetPool(ip: String,
                       pool: String,
                       create_pool: Boolean)
case object MIpsSetPool {
  implicit val writes = Json.writes[MIpsSetPool]
}

/**
  * The dns
  *
  * @param ip     - a dedicated IP address
  * @param domain - the domain name to test
  */
case class MIpsDns(ip: String,
                   domain: String)
case object MIpsDns {
  implicit val writes = Json.writes[MIpsDns]
}



