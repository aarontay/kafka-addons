package com.landoop.kafka.connect

import org.slf4j.LoggerFactory

trait Logging {
  val loggerName = this.getClass.getName
  lazy val log = LoggerFactory.getLogger(loggerName)
}
