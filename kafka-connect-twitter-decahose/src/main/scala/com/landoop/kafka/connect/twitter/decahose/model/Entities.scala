package com.landoop.kafka.connect.twitter.decahose.model

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}
import scala.collection.JavaConverters._

object Entities {

  val hschema = SchemaBuilder.struct().name("com.landoop.kafka.connect.twitter.decahose.Hashtag")
    .field("text", Schema.STRING_SCHEMA)
    .build()

  val mschema = SchemaBuilder.struct().name("com.landoop.kafka.connect.twitter.decahose.Medium")
    .field("display_url", Schema.STRING_SCHEMA)
    .field("expanded_url", Schema.STRING_SCHEMA)
    .field("id", Schema.INT64_SCHEMA)
    .field("type", Schema.STRING_SCHEMA)
    .field("url", Schema.STRING_SCHEMA)
    .build()

  val uschema = SchemaBuilder.struct().name("com.landoop.kafka.connect.twitter.decahose.Url")
    .field("display_url", Schema.STRING_SCHEMA)
    .field("expanded_url", Schema.STRING_SCHEMA)
    .field("url", Schema.STRING_SCHEMA)
    .build()

  val umschema = SchemaBuilder.struct().name("com.landoop.kafka.connect.twitter.decahose.UserMention")
    .field("id", Schema.INT64_SCHEMA)
    .field("name", Schema.STRING_SCHEMA)
    .field("screen_name", Schema.STRING_SCHEMA)
    .build()

  val schema = SchemaBuilder.struct().name("com.landoop.kafka.connect.twitter.decahose.Entities")
    .field("hashtags", SchemaBuilder.array(hschema).optional.build())
    .field("media", SchemaBuilder.array(mschema).optional.build())
    .field("urls", SchemaBuilder.array(uschema).optional.build())
    .field("user_mentions", SchemaBuilder.array(umschema).optional.build())
    .build()

  def struct(s: twitter4j.EntitySupport) =
    new Struct(schema)
      .put("hashtags", s.getHashtagEntities.toSeq.map(h =>
        new Struct(hschema)
          .put("text", h.getText)).asJava)
      .put("media", s.getMediaEntities.toSeq.map(m =>
        new Struct(mschema)
          .put("display_url", m.getDisplayURL)
          .put("expanded_url", m.getExpandedURL)
          .put("id", m.getId)
          .put("type", m.getType)
          .put("url", m.getURL)).asJava)
      .put("urls", s.getURLEntities.toSeq.map(u =>
        new Struct(uschema)
          .put("display_url", u.getDisplayURL)
          .put("expanded_url", u.getExpandedURL)
          .put("url", u.getURL)).asJava)
      .put("user_mentions", s.getUserMentionEntities.toSeq.map(um =>
        new Struct(umschema)
          .put("id", um.getId)
          .put("name", um.getName)
          .put("screen_name", um.getScreenName)).asJava)

}
