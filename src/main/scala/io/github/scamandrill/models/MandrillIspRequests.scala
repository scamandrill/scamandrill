package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The Isp
  *
  * @param id  - th unique identifier
  */
case class MIspId(id: String)

case object MIspId {
  implicit val writes = Json.writes[MIspId]
}
/**
  * The Isp
  *
  * @param ip  - a dedicated IP address
  */
case class MIspIp(ip: String)
case object MIspIp {
  implicit val writes = Json.writes[MIspIp]
}

/**
  * The Isp pool
  *
  * @param warmup - whether the ip is currently in warmup mode
  * @param pool   - the name of the new pool to add the dedicated ip to
  */
case class MIspPool(warmup: Boolean,
                    pool: String)
case object MIspPool {
  implicit val writes = Json.writes[MIspPool]
}

/**
  * The Isp pool info
  *
  * @param pool - the name of the new pool to add the dedicated ip to
  */
case class MIspPoolInfo(pool: String)
case object MIspPoolInfo {
  implicit val writes = Json.writes[MIspPoolInfo]
}

/**
  * The Isp pool info
  *
  * @param ip          - a dedicated IP address
  * @param pool        - the name of the new pool to add the dedicated ip to
  * @param create_pool - whether to create the pool if it does not exist; if false and the pool does not exist, an Unknown_Pool will be thrown.
  */
case class MIspSetPool(ip: String,
                       pool: String,
                       create_pool: Boolean)
case object MIspSetPool {
  implicit val writes = Json.writes[MIspSetPool]
}

/**
  * The dns
  *
  * @param ip     - a dedicated IP address
  * @param domain - the domain name to test
  */
case class MIspDns(ip: String,
                   domain: String)
case object MIspDns {
  implicit val writes = Json.writes[MIspDns]
}



