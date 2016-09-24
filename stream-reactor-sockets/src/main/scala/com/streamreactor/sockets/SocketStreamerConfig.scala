package com.streamreactor.sockets

import java.net.URL

import com.typesafe.config.ConfigFactory

import scala.collection.JavaConversions._
import scala.util.Try

case class SocketStreamerConfig(actorSystemName: String,
                                zookeeper: String,
                                kafkaBrokers: String,
                                schemaRegistryUrl: String,
                                port: Int,
                                consumerProperties: Map[String, String])

object SocketStreamerConfig {
  def apply() = {
    val config = ConfigFactory.load()
    val systemName = config.getString("system-name")
    require(systemName.trim.length > 0, "Invalid actor system name")

    val zookeepers = config.getString("kafka.zookeeper")
    require(zookeepers.trim.length > 0, "Invalid zookeeper")
    val kafkaBootstrapServers = config.getString("kafka.brokers")

    val schemaRegistryUrl = config.getString("kafka.schema-registry-url")
    require(schemaRegistryUrl.trim.length > 0 && Try(new URL(schemaRegistryUrl)).isSuccess, "Invalid Schema Registry URL")

    val port = config.getInt("port")
    val kafkaClientConfigs = Try(config.getConfig("kafka.client"))
      .map { c =>
        c.entrySet().map { e => e.getKey -> e.getValue.unwrapped().toString }.toMap
      }.getOrElse(Map.empty)
    new SocketStreamerConfig(systemName, zookeepers, kafkaBootstrapServers, schemaRegistryUrl, port, kafkaClientConfigs)
  }

}
