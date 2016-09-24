package com.landoop.kafka.connect.twitter.decahose.support

import com.landoop.kafka.connect.twitter.decahose.TwitterSourceConfig
import scala.collection.JavaConverters._

trait MockConfiguration {

  val getConfig =
    Map(TwitterSourceConfig.TWITTER_USERNAME -> "some-username",
      TwitterSourceConfig.TWITTER_PASSWORD -> "some-password",
      TwitterSourceConfig.TWITTER_URL -> "decahose-url-comma-separated-if-more-than-one",
      TwitterSourceConfig.TWITTER_APP_NAME -> "some-app-name",
      TwitterSourceConfig.TOPIC -> "some-topic").asJava

}
