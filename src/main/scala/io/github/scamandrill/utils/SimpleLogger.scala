package io.github.scamandrill.utils

import org.slf4j.LoggerFactory

trait SimpleLogger {
  val logger = LoggerFactory.getLogger(getClass)
}
