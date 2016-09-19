package com.landoop.kafka.connect.twitter.decahose

trait MockConfiguration {

  val getConfig =
    Map(TwitterSourceConfig.TWITTER_USERNAME -> "test",
      TwitterSourceConfig.TWITTER_PASSWORD -> "c-secret",
      TwitterSourceConfig.TWITTER_URL -> "token",
      TwitterSourceConfig.TWITTER_APP_NAME -> "myApp",
      TwitterSourceConfig.TOPIC -> "just-a-topic"
    )

}
