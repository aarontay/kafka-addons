package com.landoop.kafka.connect.twitter.decahose

import org.apache.kafka.connect.errors.ConnectException
import org.scalatest.{FunSuite, Matchers}

import scala.collection.JavaConverters._

class TestTwitterSourceConnectorSpec extends FunSuite with Matchers with MockConfiguration {

  val badProps = getConfig + (TwitterSourceConfig.BATCH_SIZE -> "this is no integer")

  test("A TwitterSourceConnector should start with valid properties") {
    val t = new TwitterSourceConnector()
    t.start(getConfig.asJava)
  }

  test("A TwitterSourceConnector shouldn't start with invalid properties") {
    val t = new TwitterSourceConnector()
    a[ConnectException] should be thrownBy {
      t.start(badProps.asJava)
    }
  }

  test("A TwitterSourceConnector should provide the correct taskClass") {
    val t = new TwitterSourceConnector()
    t.taskClass() should be(classOf[TwitterSourceTask])
  }

  test("A TwitterSourceConnector should return a taskConfig for each task") {
    val t = new TwitterSourceConnector()
    t.taskConfigs(42).size() should be(42)
  }
}
