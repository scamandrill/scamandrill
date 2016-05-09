package com.joypeg.scamandrill

import com.joypeg.scamandrill.utils.SimpleLogger
import org.scalatest.{FlatSpec, Matchers, Retries}

/**
  * Created by graingert on 09/05/16.
  */
trait MandrillSpec extends FlatSpec with Matchers with SimpleLogger with Retries {
  override def withFixture(test: NoArgTest) = {
    if (isRetryable(test))
      withRetry { super.withFixture(test) }
    else
      super.withFixture(test)
  }
}