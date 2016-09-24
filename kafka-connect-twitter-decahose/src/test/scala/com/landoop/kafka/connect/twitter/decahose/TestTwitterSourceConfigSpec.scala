package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.support.MockConfiguration
import org.scalatest.{FunSuite, Matchers}

class TestTwitterSourceConfigSpec extends FunSuite with Matchers with MockConfiguration {

  test("A TwitterSourceConfig should be correctly configured") {
    val taskConfig = new TwitterSourceConfig(getConfig)
    taskConfig.getString(TwitterSourceConfig.TWITTER_USERNAME) shouldBe "some-username"
    taskConfig.getPassword(TwitterSourceConfig.TWITTER_PASSWORD).value shouldBe "some-password"
    taskConfig.getString(TwitterSourceConfig.TWITTER_URL) shouldBe "decahose-url-comma-separated-if-more-than-one"
    taskConfig.getString(TwitterSourceConfig.TWITTER_APP_NAME) shouldBe "some-app-name"
    taskConfig.getString(TwitterSourceConfig.TOPIC) shouldBe "some-topic"
  }

}
