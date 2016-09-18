package com.landoop.kafka.connect.slack

object Config {

  val kafkaTopicDirective = "kafka.topic"
  val slackApiKeyDirective = "slack.apikey"

  val graphiteHost = "graphite.landoop.com"
  val graphitePort = 2003
  val graphiteKey = "kafka.connect.slack.source"

}
