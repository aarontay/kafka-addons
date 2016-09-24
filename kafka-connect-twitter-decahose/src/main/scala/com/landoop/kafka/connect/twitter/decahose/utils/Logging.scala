package com.landoop.kafka.connect.twitter.decahose.utils

import org.slf4j.LoggerFactory

trait Logging {
  val loggerName = this.getClass.getName
  @transient lazy val log = LoggerFactory.getLogger(loggerName)
}