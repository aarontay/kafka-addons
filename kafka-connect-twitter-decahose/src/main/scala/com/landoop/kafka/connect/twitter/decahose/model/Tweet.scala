package com.landoop.kafka.connect.twitter.decahose.model

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

case class Tweet(id: String,
                 objectType: String,
                 verb: String,
                 postedTime: String,
                 link: String, // i.e. "http:\/\/twitter.com\/hhusted\/statuses\/780843014214283265",
                 body: String, // i.e. "body": "@Cnvrswrld @KieferBobbie @Always_Trump No. Because they are blinded like everyone else by the globalist. Wake up.",
                 hash: String,
                 relayed_by: String,
                 twitter_lang: String, // i.e. "en"
                 twitter_filter_level: String, // i.e. "low"
                 time: Long,
                 actor: Actor,
                 generator: Generator,
                 provider: Provider,
                 display_text_range: List[Int], // i.e. [39, 113]
                 tx_index: Long,
                 retweetCount: Int,
                 vin_sz: Int,
                 vout_sz: Int,
                 `object`: Object,
                 inReplyTo: InReplyTo,
                 twitter_entities: TwitterEntities
                )

object Tweet {

  val connectSchema: Schema = SchemaBuilder.struct
    .name("streamreactor.twitter.decahose.tweet")
    .doc("The input instance part of a transaction.")
    .field("id", Schema.STRING_SCHEMA)
    .field("objectType", Schema.STRING_SCHEMA)
    .field("verb", Schema.STRING_SCHEMA)
    .field("postedTime", Schema.STRING_SCHEMA)
    .field("generator", Generator.ConnectSchema)
    .field("provider", Provider.ConnectSchema)
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
