package io.github.scamandrill.models

import play.api.libs.json.Json

/**
  * The search information
  *
  * @param q   - a search query
  */
case class MUrlSearch(q: String)
case object MUrlSearch {
  implicit val writes = Json.writes[MUrlSearch]
}

/**
  * An url for the time series
  *
  * @param url - an existing URL
  */
case class MUrlTimeSeries(url: String)
case object MUrlTimeSeries {
  implicit val writes = Json.writes[MUrlTimeSeries]
}

/**
  * A valid domain
  *
  * @param domain - a domain name
  */
case class MUrlDomain(domain: String)
case object MUrlDomain {
  implicit val writes = Json.writes[MUrlDomain]
}
