package com.landoop.kafka.connect.twitter.decahose.avro

import com.landoop.kafka.connect.twitter.decahose.avro
import org.apache.kafka.connect.data.{Schema, SchemaBuilder}

object Tweet {

  val connectSchema: Schema = SchemaBuilder.struct
    .name("streamreactor.twitter.decahose.tweet")
    .doc("The input instance part of a transaction.")
    .field("id", Schema.STRING_SCHEMA)
    .field("objectType", Schema.STRING_SCHEMA)
    .field("verb", Schema.STRING_SCHEMA)
    .field("postedTime", Schema.STRING_SCHEMA)
    .field("generator", avro.Generator.ConnectSchema)
    .field("provider", avro.Provider.ConnectSchema)
    .field("link", Schema.STRING_SCHEMA)
    .field("body", Schema.STRING_SCHEMA)
    .field("display_text_range", SchemaBuilder.array(Schema.INT32_SCHEMA).build)
    .field("time", Schema.INT32_SCHEMA)
    .field("tx_index", Schema.INT64_SCHEMA)
    .field("vin_sz", Schema.INT16_SCHEMA)
    .field("hash", Schema.STRING_SCHEMA)
    .field("vout_sz", Schema.INT16_SCHEMA)
    .field("relayed_by", Schema.STRING_SCHEMA)
    .field("actor", Actor.ConnectSchema)

    .build()

  // .field("generator", SchemaBuilder.array(Generator.ConnectSchema).optional.build)
  // .field("provider", SchemaBuilder.array(Provider.ConnectSchema).optional.build)
  // .field("rbf", Schema.OPTIONAL_BOOLEAN_SCHEMA)
  // .field("out", SchemaBuilder.array(Output.ConnectSchema).optional().build())

}