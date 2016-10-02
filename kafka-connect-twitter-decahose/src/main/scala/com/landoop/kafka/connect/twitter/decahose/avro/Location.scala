package com.landoop.kafka.connect.twitter.decahose.avro

import com.landoop.kafka.connect.twitter.decahose.model.Location
import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

object Location {

  val ConnectSchema = SchemaBuilder.struct
    .name("streamreactor.twitter.decahose.tweet.location")
    .doc("Href link mentioned on the tweet")
    .field("objectType", Schema.STRING_SCHEMA)
    .field("displayName", Schema.STRING_SCHEMA)
    .build()

  implicit class GeneratorToStructConverter(val location: Location) extends AnyVal {
    def toStruct: Struct =
      new Struct(ConnectSchema)
        .put("objectType", location.objectType)
        .put("displayName", location.objectType)
  }

}
