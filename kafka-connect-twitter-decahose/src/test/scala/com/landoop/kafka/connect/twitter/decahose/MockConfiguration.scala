package com.landoop.kafka.connect.twitter.decahose

trait MockConfiguration {

  val getConfig =
    Map(TwitterSourceConfig.CONSUMER_KEY_CONFIG -> "test",
      TwitterSourceConfig.CONSUMER_SECRET_CONFIG -> "c-secret",
      TwitterSourceConfig.SECRET_CONFIG -> "secret",
      TwitterSourceConfig.TOKEN_CONFIG -> "token",
      TwitterSourceConfig.TRACK_TERMS -> "term1",
      TwitterSourceConfig.TWITTER_APP_NAME -> "myApp",
      TwitterSourceConfig.BATCH_SIZE -> "1337",
      TwitterSourceConfig.TOPIC -> "just-a-topic"
    )

}
