package com.landoop.kafka.connect.twitter.decahose

import org.apache.kafka.common.config.{AbstractConfig, ConfigDef}
import org.apache.kafka.common.config.ConfigDef.{Type, Importance}
import java.util

object DecahoseConfig {

  val GNIP_USERNAME = "gnip.username"
  val GNIP_USERNAME_DOC = "Username at Gnip"

  val GNIP_PASSWORD = "gnip.password"
  val GNIP_PASSWORD_DOC = "Password at Gnip"

  val GNIP_ENDPOINTS = "gnip.endpoints"
  val GNIP_ENDPOINTS_DOC = "Decahose endpoints, comma separate for multiple url-partitions"

  val TOPIC = "topic"
  val TOPIC_DOC = "The Kafka topic to write data to"
  val TOPIC_DEFAULT = "twitter-decahose"

  val config: ConfigDef = new ConfigDef()
    .define(GNIP_USERNAME, Type.STRING, Importance.HIGH, GNIP_USERNAME_DOC)
    .define(GNIP_PASSWORD, Type.PASSWORD, Importance.HIGH, GNIP_PASSWORD_DOC)
    .define(GNIP_ENDPOINTS, Type.STRING, Importance.HIGH, GNIP_ENDPOINTS_DOC)
    .define(TOPIC, Type.STRING, TOPIC_DEFAULT, Importance.HIGH, TOPIC_DOC)

}

class DecahoseConfig(props: util.Map[String, String]) extends AbstractConfig(DecahoseConfig.config, props) {
  val username = getString(DecahoseConfig.GNIP_USERNAME)
  val password = getString(DecahoseConfig.GNIP_PASSWORD)
  val endpoints = getList(DecahoseConfig.GNIP_ENDPOINTS)
  val topic = getString(DecahoseConfig.TOPIC)
  if (username.length == 0)
    throw new RuntimeException("You need to provide a gnip username")
  if (password.length == 0)
    throw new RuntimeException("You need to provide a gnip password")
  if (endpoints.isEmpty)
    throw new RuntimeException("You need to provide at least one gnip endpoint")
}
