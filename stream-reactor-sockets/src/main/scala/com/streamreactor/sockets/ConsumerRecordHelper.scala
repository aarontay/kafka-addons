package com.streamreactor.sockets

import java.util.Base64

import akka.http.scaladsl.model.ws.TextMessage
import com.streamreactor.sockets.avro.AvroJsonSerializer._
import com.streamreactor.sockets.avro.FieldsValuesExtractor
import com.streamreactor.sockets.domain.StreamMessage
import de.heikoseeberger.akkasse.ServerSentEvent
import kafka.serializer.Decoder
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord

object ConsumerRecordHelper {

  implicit class ConsumerRecordConverter(val record: ConsumerRecord[Array[Byte], Array[Byte]]) {

    /**
      * Convert the Kafka consumer record to a json string
      *
      * @param decoder The Kafka avro decoder
      * @return A Json string representing a StreamMessage
      **/
    def toJson()(implicit decoder: Decoder[AnyRef], fieldsValuesExtractor: FieldsValuesExtractor) = {
      //using confluent's decoder
      val key = Option(record.key())
        .map(key => decoder.fromBytes(key))
        .map {
          case r: GenericRecord => r.toJson
          case s: String => s
          case b: Array[Byte] => Base64.getEncoder.encodeToString(b)
          case other => other.toString
        }

      val payload = Option(record.value)
        .map(value => decoder.fromBytes(value))
        .map {
          case g: GenericRecord => JacksonJson.toJson(fieldsValuesExtractor.get(g))
          case s: String => s
          case b: Array[Byte] => Base64.getEncoder.encodeToString(b)
          case other => other.toString
        }

      val msg = StreamMessage(record.topic(),
        record.partition(),
        record.timestamp(),
        record.timestampType().toString,
        key,
        payload)
      JacksonJson.toJson(msg)
    }

    /**
      * Converts the ConsumerRecord to json
      *
      * @param decoder The Kafka avro decoder
      * @return A TextMessage with a WebMessage Json string
      */
    def toWSMessage()(implicit decoder: Decoder[AnyRef], fieldsValuesExtractor: FieldsValuesExtractor) = TextMessage.Strict(toJson)

    /**
      * Convert a ConsumerRecord to a ServerSendEvent
      *
      * @param decoder The Kafka avro decoder
      * @return A instance of ServerSendEvent
      */
    def toSSEMessage()(implicit decoder: Decoder[AnyRef], fieldsValuesExtractor: FieldsValuesExtractor) = ServerSentEvent(toJson)

  }

}
