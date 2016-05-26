package io.github.scamandrill.models

/**
  * The Isp
  *
  * @param id  - th unique identifier
  */
case class MIspId(id: String) extends MandrillRequest

/**
  * The Isp
  *
  * @param ip  - a dedicated IP address
  */
case class MIspIp(ip: String) extends MandrillRequest

/**
  * The Isp pool
  *
  * @param warmup - whether the ip is currently in warmup mode
  * @param pool   - the name of the new pool to add the dedicated ip to
  */
case class MIspPool(warmup: Boolean,
                    pool: String) extends MandrillRequest

/**
  * The Isp pool info
  *
  * @param pool - the name of the new pool to add the dedicated ip to
  */
case class MIspPoolInfo(pool: String) extends MandrillRequest

/**
  * The Isp pool info
  *
  * @param ip          - a dedicated IP address
  * @param pool        - the name of the new pool to add the dedicated ip to
  * @param create_pool - whether to create the pool if it does not exist; if false and the pool does not exist, an Unknown_Pool will be thrown.
  */
case class MIspSetPool(ip: String,
                       pool: String,
                       create_pool: Boolean) extends MandrillRequest

/**
  * The dns
  *
  * @param ip     - a dedicated IP address
  * @param domain - the domain name to test
  */
case class MIspDns(ip: String,
                   domain: String) extends MandrillRequest



