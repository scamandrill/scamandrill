package io.github.scamandrill.models

/**
  * The search information
  *
  * @param q   - a search query
  */
case class MUrlSearch(q: String) extends MandrillRequest

/**
  * An url for the time series
  *
  * @param url - an existing URL
  */
case class MUrlTimeSeries(url: String) extends MandrillRequest

/**
  * A valid domain
  *
  * @param domain - a domain name
  */
case class MUrlDomain(domain: String) extends MandrillRequest
