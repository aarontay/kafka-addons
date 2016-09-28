package com.landoop.kafka.connect.twitter.decahose.model

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

case class Generator(displayName: String,
                     link: String)

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
