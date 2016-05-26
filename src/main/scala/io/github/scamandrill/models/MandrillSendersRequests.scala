package io.github.scamandrill.models

/**
  * The sender domain
  *
  * @param domain - a domain name at which you can receive email
  */
case class MSenderDomain(domain: String) extends MandrillRequest

/**
  * The sender domain
  *
  * @param domain  - a domain name at which you can receive email
  * @param mailbox - a mailbox at the domain where the verification email should be sent
  */
case class MSenderVerifyDomain(domain: String,
                               mailbox: String) extends MandrillRequest

/**
  * The sender address
  *
  * @param address - the email address of the sender
  */
case class MSenderAddress(address: String) extends MandrillRequest
