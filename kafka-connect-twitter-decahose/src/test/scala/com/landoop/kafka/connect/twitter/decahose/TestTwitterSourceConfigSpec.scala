package com.landoop.kafka.connect.twitter.decahose

import org.scalatest.{FunSuite, Matchers}
import scala.collection.JavaConverters._

class TestTwitterSourceConfigSpec extends FunSuite with Matchers with MockConfiguration {

  test("A TwitterSourceConfig should be correctly configured") {
    val config = getConfig
    val taskConfig = new TwitterSourceConfig(config.asJava)
    taskConfig.getString(TwitterSourceConfig.TWITTER_USERNAME) shouldBe "test"
    taskConfig.getPassword(TwitterSourceConfig.TWITTER_PASSWORD).value shouldBe "c-secret"
    taskConfig.getString(TwitterSourceConfig.TWITTER_URL) shouldBe "token"
    taskConfig.getString(TwitterSourceConfig.TWITTER_APP_NAME) shouldBe "myApp"
    taskConfig.getString(TwitterSourceConfig.TOPIC) shouldBe "just-a-topic"
  }

}
