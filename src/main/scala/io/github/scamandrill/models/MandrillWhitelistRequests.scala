package io.github.scamandrill.models

/**
  * An email to be added retrieved or deleted from the whitelist
  *
  * @param email - an email address for the whitelist
  */
case class MWhitelist(email: String) extends MandrillRequest
