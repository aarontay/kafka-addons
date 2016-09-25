package com.landoop.kafka.connect.twitter.decahose.support

import com.landoop.kafka.connect.twitter.decahose.DecahoseConfig
import scala.collection.JavaConverters._

trait MockConfiguration {

  val getConfig =
    Map(DecahoseConfig.GNIP_USERNAME -> "some-username",
      DecahoseConfig.GNIP_PASSWORD -> "some-password",
      DecahoseConfig.GNIP_ENDPOINTS -> "decahose-url-comma-separated-if-more-than-one",
      DecahoseConfig.TOPIC -> "some-topic").asJava

}
