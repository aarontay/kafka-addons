package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.support.MockConfiguration
import org.apache.kafka.connect.errors.ConnectException
import org.scalatest.{FunSuite, Matchers}
import scala.collection.JavaConverters._

class TestTwitterSourceConnectorSpec extends FunSuite with Matchers with MockConfiguration {

  val myScalaMap = getConfig.asScala.+("TwitterSourceConfig.BATCH_SIZE" -> "this is no integer")

  test("A TwitterDecahoseConnector should start with valid properties") {
    val t = new TwitterSourceConnector()
    t.start(getConfig)
  }

  //  test("A TwitterSourceConnector shouldn't start with invalid properties") {
  //    val t = new TwitterSourceConnector()
  //    a[ConnectException] should be thrownBy {
  //      t.start(badProps.asJava)
  //    }
  //  }

  test("A TwitterDecahoseConnector  should provide the correct taskClass") {
    val t = new TwitterSourceConnector()
    t.taskClass() should be(classOf[DecahoseTask])
  }

  test("A TwitterDecahoseConnector should return a taskConfig for each task") {
    val t = new TwitterSourceConnector()
    t.taskConfigs(42).size() should be(42)
  }
}
