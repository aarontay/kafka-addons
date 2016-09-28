package com.landoop.kafka.connect.twitter.decahose.model

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

case class Provider(objectType: String, // i.e. "service"
                    displayName: String, // i.e. "Twitter"
                    link: String // i.e. "http:\/\/www.twitter.com"
                   )

object Provider {

  val ConnectSchema = SchemaBuilder.struct
    .name("streamreactor.twitter.decahose.tweet.provider")
    .doc("The input instance part of a transaction.")
    .field("objectType", Schema.STRING_SCHEMA)
    .field("displayName", Schema.STRING_SCHEMA)
    .field("link", Schema.STRING_SCHEMA)
    .build()

  implicit class ProviderToStructConverter(val generator: Provider) extends AnyVal {
    def toStruct = {
      new Struct(ConnectSchema)
        .put("objectType", generator.objectType)
        .put("displayName", generator.displayName)
        .put("link", generator.link)
    }
  }

}
