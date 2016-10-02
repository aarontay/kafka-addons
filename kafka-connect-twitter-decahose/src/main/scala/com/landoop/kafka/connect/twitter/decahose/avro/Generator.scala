package com.landoop.kafka.connect.twitter.decahose.avro

import com.landoop.kafka.connect.twitter.decahose.model.Generator
import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

object Generator {

  val ConnectSchema = SchemaBuilder.struct
    .name("streamreactor.twitter.decahose.tweet.generator")
    .doc("The input instance part of a transaction.")
    .field("displayName", Schema.STRING_SCHEMA)
    .field("link", Schema.STRING_SCHEMA)
    .build()

  implicit class GeneratorToStructConverter(val generator: Generator) extends AnyVal {
    def toStruct: Struct =
      new Struct(ConnectSchema)
        .put("displayName", generator.displayName)
        .put("link", generator.link)
  }

}
