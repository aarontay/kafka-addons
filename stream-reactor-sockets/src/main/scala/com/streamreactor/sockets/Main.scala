package com.streamreactor.sockets

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.RouteResult.route2HandlerFlow
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.streamreactor.sockets.avro.{BinaryDecoder, StringDecoder}
import com.streamreactor.sockets.routes.KafkaSocketRoutes
import com.typesafe.scalalogging.StrictLogging

import scala.concurrent.duration.DurationInt

object Main extends App with StrictLogging {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()
  implicit val timeout = Timeout(1000.millis)
  logger.info(
    """
      |
      |         __                                                       __                                __        __
      |   _____/ /_________  ____ _____ ___        ________  ____ ______/ /_____  _____   _________  _____/ /_____  / /______
      |  / ___/ __/ ___/ _ \/ __ `/ __ `__ \______/ ___/ _ \/ __ `/ ___/ __/ __ \/ ___/  / ___/ __ \/ ___/ //_/ _ \/ __/ ___/
      | (__  ) /_/ /  /  __/ /_/ / / / / / /_____/ /  /  __/ /_/ / /__/ /_/ /_/ / /     (__  ) /_/ / /__/ ,< /  __/ /_(__  )
      |/____/\__/_/   \___/\__,_/_/ /_/ /_/     /_/   \___/\__,_/\___/\__/\____/_/     /____/\____/\___/_/|_|\___/\__/____/
      |
      |
    """.stripMargin)

  implicit val configuration = SocketStreamerConfig()

  logger.info(
    s"""
       |System name      : ${configuration.actorSystemName}
       |Kafka brokers    : ${configuration.kafkaBrokers}
       |Zookeepers       : ${configuration.zookeeper}
       |Schema registry  : ${configuration.schemaRegistryUrl}
       |Listening on port : ${configuration.port}
    """.stripMargin)

  val kafkaAvroDecoder = KafkaAvroDecoderFn(configuration)
  val flowRoute = KafkaSocketRoutes(system, configuration, kafkaAvroDecoder, StringDecoder, BinaryDecoder)
  val serverBinding = Http().bindAndHandle(interface = "0.0.0.0", port = configuration.port, handler = flowRoute.routes)
  println("Started")
}
