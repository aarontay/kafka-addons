package com.streamreactor.sockets.routes

import com.streamreactor.sockets.{JacksonJson, SocketStreamerConfig}
import com.streamreactor.sockets.domain.{HeartBeatMessage, KafkaClientProps, KafkaStreamingProps}
import com.streamreactor.sockets.flows.KafkaSourceCreateFn
import com.typesafe.scalalogging.StrictLogging
import akka.actor.ActorSystem
import akka.http.scaladsl.model.ws.{Message, TextMessage}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.{Route, ValidationRejection}
import akka.kafka.scaladsl.Consumer
import akka.kafka.scaladsl.Consumer.Control
import akka.kafka.{ConsumerSettings, KafkaConsumerActor, Subscriptions}
import akka.stream.scaladsl.{Flow, Sink, Source}
import com.streamreactor.sockets.ConsumerRecordHelper._
import com.streamreactor.sockets.avro.GenericRecordFieldsValuesExtractor
import de.heikoseeberger.akkasse.{EventStreamMarshalling, ServerSentEvent}
import io.confluent.kafka.serializers.KafkaAvroDecoder
import kafka.serializer.Decoder
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.TopicPartition
import org.apache.kafka.common.serialization.ByteArrayDeserializer
import EventStreamMarshalling._
import scala.concurrent.duration._
import scala.util.{Failure, Success, Try}

case class KafkaSocketRoutes(system: ActorSystem,
                             config: SocketStreamerConfig,
                             kafkaDecoder: KafkaAvroDecoder,
                             textDcoder: Decoder[AnyRef],
                             binaryDecoder: Decoder[AnyRef]) extends StrictLogging {

  private implicit val actorSystem = system
  private implicit val socketStreamConfig = config

  private val consumerSettings = ConsumerSettings(system, new ByteArrayDeserializer, new ByteArrayDeserializer)
    .withBootstrapServers(config.kafkaBrokers)
    .withGroupId("streamreactorsockets")
    //if an offset is out of range or the offset doesn't exist yet default to earliest available
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  private val schemasConsumerRef = system.actorOf(KafkaConsumerActor.props(consumerSettings))

  private val schemaSource = Consumer
    .plainExternalSource[Array[Byte], Array[Byte]](schemasConsumerRef, Subscriptions.assignmentWithOffset(new TopicPartition("_schemas", 0), 0))

  private lazy val webSocketFlowForSchemasTopic: Flow[Message, Message, Any] = {
    implicit val extractor = GenericRecordFieldsValuesExtractor(includeAllFields = true, Map.empty)
    implicit val decoder = textDcoder

    Flow.fromSinkAndSource(Sink.ignore, schemaSource.map(_.toWSMessage()))
      .keepAlive(1.second, () => TextMessage.Strict(""))
  }

  private lazy val sseSocketFlowForSchemasTopic: Source[ServerSentEvent, Any] = {
    implicit val extractor = GenericRecordFieldsValuesExtractor(true, Map.empty)
    implicit val decoder = textDcoder

    schemaSource
      .map(_.toSSEMessage())
      .keepAlive(1.second, () => ServerSentEvent(JacksonJson.toJson(HeartBeatMessage(system.name))))
  }

  val routes: Route = {
    pathPrefix("api" / "kafka") {
      pathPrefix("ws") {
        get {
          parameter('query) { query =>
            withKafkaStreamingProps(query) { props =>
              props.topic.toLowerCase() match {
                case "_schemas" =>
                  handleWebSocketMessages(webSocketFlowForSchemasTopic)
                case _ => handleWebSocketMessages(createWebSocketFlow(props))
              }
            }
          }
        }
      } ~
        path("sse") {
          get {
            parameter('query) { query =>
              implicit val decoder: Decoder[AnyRef] = kafkaDecoder
              withKafkaStreamingProps(query) { props =>
                props.topic.toLowerCase() match {
                  case "_schemas" => complete(sseSocketFlowForSchemasTopic)
                  case _ => complete(createServerSendFlow(props))
                }
              }
            }
          }
        }
    }
  }

  private def withKafkaStreamingProps(query: String)(thunk: KafkaStreamingProps => Route) = {
    Try(KafkaStreamingProps(query)(kafkaDecoder, textDcoder, binaryDecoder)) match {
      case Failure(t) =>
        reject(ValidationRejection(s"Invalid query:$query. ${t.getMessage}"))
      case Success(prop) => thunk(prop)
    }
  }

  /**
    * Create a flow with a null sink (don't accept incoming websocket data) and a source from Kafka out to the websocket
    *
    * @param props A KafkaStreamingProps to use to create a KafkaConsumer
    * @return a Flow with a null inbound sink and a ReactiveKafka publisher source
    **/
  private def createWebSocketFlow(props: KafkaStreamingProps): Flow[Message, Message, Any] = {
    logger.info("Establishing flow")
    val kafkaSource = KafkaSourceCreateFn(props)
    implicit val extractor = props.fieldsValuesExtractor
    implicit val decoder = props.decoder
    val flow: Flow[Message, Message, Any] = Flow
      .fromSinkAndSource(Sink.ignore, kafkaSource.map(_.toWSMessage))
      .keepAlive(1.second, () => TextMessage.Strict(""))
    flow
  }

  /**
    * Create one directional flow of ServerSendEvents
    *
    * @param props A KafkaRequestProps to use to create a KafkaConsumer
    * @return a ReactiveKafka publisher source
    **/
  private def createServerSendFlow(props: KafkaStreamingProps): Source[ServerSentEvent, Control] = {
    logger.info("Establishing Send Server Event stream.")
    implicit val extractor = props.fieldsValuesExtractor
    implicit val decoder = props.decoder
    //get the kafka source
    KafkaSourceCreateFn(props)
      .map(_.toSSEMessage)
      .keepAlive(1.second, () => ServerSentEvent(JacksonJson.toJson(HeartBeatMessage(system.name))))
  }

  implicit def convert(props: KafkaStreamingProps): KafkaClientProps = {
    KafkaClientProps(props.topic, props.group, props.partitionOffset, props.sampleProps)
  }
}
