package com.joypeg.scamandrill.models

case class MTagRequest(key: String = DefaultConfig.defaultKeyFromConfig, tag: String) extends MandrillRequest
