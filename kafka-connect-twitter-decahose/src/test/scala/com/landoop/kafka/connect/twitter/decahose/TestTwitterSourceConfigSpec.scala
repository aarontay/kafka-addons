package com.landoop.kafka.connect.twitter.decahose

import org.scalatest.{FunSuite, Matchers}
import scala.collection.JavaConverters._

class TestTwitterSourceConfigSpec extends FunSuite with Matchers {

  val getConfig = {
    Map(TwitterSourceConfig.CONSUMER_KEY_CONFIG->"test",
      TwitterSourceConfig.CONSUMER_SECRET_CONFIG->"c-secret",
      TwitterSourceConfig.SECRET_CONFIG->"secret",
      TwitterSourceConfig.TOKEN_CONFIG->"token",
      TwitterSourceConfig.TRACK_TERMS->"term1",
      TwitterSourceConfig.TWITTER_APP_NAME->"myApp",
      TwitterSourceConfig.BATCH_SIZE->"1337",
      TwitterSourceConfig.TOPIC->"just-a-topic"
    )
  }

  test("A TwitterSourceConfig should be correctly configured") {
    val config = getConfig
    val taskConfig = new TwitterSourceConfig(config.asJava)
    taskConfig.getString(TwitterSourceConfig.CONSUMER_KEY_CONFIG) shouldBe "test"
    taskConfig.getPassword(TwitterSourceConfig.SECRET_CONFIG).value shouldBe "secret"
    taskConfig.getPassword(TwitterSourceConfig.CONSUMER_SECRET_CONFIG).value shouldBe "c-secret"
    taskConfig.getString(TwitterSourceConfig.TOKEN_CONFIG) shouldBe "token"
    taskConfig.getList(TwitterSourceConfig.TRACK_TERMS).asScala.head shouldBe "term1"
    taskConfig.getString(TwitterSourceConfig.TWITTER_APP_NAME) shouldBe "myApp"
    taskConfig.getInt(TwitterSourceConfig.BATCH_SIZE) shouldBe 1337
    taskConfig.getString(TwitterSourceConfig.TOPIC) shouldBe "just-a-topic"
  }
}
