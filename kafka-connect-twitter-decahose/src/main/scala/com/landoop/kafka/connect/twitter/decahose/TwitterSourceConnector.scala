package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.utils.Logging
import org.apache.kafka.connect.connector.{Connector, Task}
import org.apache.kafka.connect.errors.ConnectException
import scala.collection.JavaConverters._
import scala.util.{Failure, Try}
import java.util

class TwitterSourceConnector extends Connector with Logging {

  private var configProps: util.Map[String, String] = _

  override def config() = TwitterSourceConfig.config

  override def taskClass(): Class[_ <: Task] = classOf[TwitterDecahoseSourceTask]

  override def taskConfigs(maxTasks: Int): util.List[util.Map[String, String]] = {
    log.info(s"Setting task configurations for $maxTasks workers.")
    (1 to maxTasks).map(c => configProps).toList.asJava
  }

  override def start(props: util.Map[String, String]): Unit = {
    log.info(s"Starting Twitter source task with ${props.toString}.")
    configProps = props
    Try(new TwitterSourceConfig(props)) match {
      case Failure(f) => throw new ConnectException("Couldn't start Twitter Decahose source due to configuration error: "
        + f.getMessage, f)
      case _ =>
    }
  }

  override def stop() = {}

  override def version(): String = ""

}
