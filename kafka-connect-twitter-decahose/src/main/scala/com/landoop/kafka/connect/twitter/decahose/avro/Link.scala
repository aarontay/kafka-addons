package com.landoop.kafka.connect.twitter.decahose.avro

import com.landoop.kafka.connect.twitter.decahose.model.Link
import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

object Link {

  val ConnectSchema = SchemaBuilder.struct
    .name("streamreactor.twitter.decahose.tweet.link")
    .doc("Href link mentioned on the tweet")
    .field("href", Schema.STRING_SCHEMA)
    .field("rel", Schema.STRING_SCHEMA)
    .build()

  implicit class GeneratorToStructConverter(val link: Link) extends AnyVal {
    def toStruct: Struct =
      new Struct(ConnectSchema)
        .put("href", link.href)
        .put("rel", link.rel)
  }

}
