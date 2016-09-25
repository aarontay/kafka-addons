package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.support.MockConfiguration
import org.scalatest.{FunSuite, Matchers}

class TestTwitterSourceConfigSpec extends FunSuite with Matchers with MockConfiguration {

  test("A TwitterSourceConfig should be correctly configured") {
    val taskConfig = new DecahoseConfig(getConfig)
    taskConfig.getString(DecahoseConfig.GNIP_USERNAME) shouldBe "some-username"
    taskConfig.getPassword(DecahoseConfig.GNIP_PASSWORD).value shouldBe "some-password"
    taskConfig.getString(DecahoseConfig.GNIP_ENDPOINTS) shouldBe "decahose-url-comma-separated-if-more-than-one"
    taskConfig.getString(DecahoseConfig.TOPIC) shouldBe "some-topic"
  }

}
