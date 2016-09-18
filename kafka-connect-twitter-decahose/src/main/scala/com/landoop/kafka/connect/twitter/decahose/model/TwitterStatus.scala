package com.landoop.kafka.connect.twitter.decahose.model

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}
import java.util.{Date, TimeZone}
import java.text.SimpleDateFormat

object TwitterStatus {
  def asIso8601String(d: Date) = {
    val tz = TimeZone.getTimeZone("UTC")
    val df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    df.setTimeZone(tz)
    df.format(if (d == null) new Date() else d)
  }

  def struct(s: twitter4j.Status) =
    new Struct(schema)
      .put("id", s.getId)
      .put("created_at", asIso8601String(s.getCreatedAt))
      .put("user", TwitterUser.struct(s.getUser))
      .put("text", s.getText)
      .put("lang", s.getLang)
      .put("is_retweet", s.isRetweet)
      .put("entities", Entities.struct(s))

  val schema = SchemaBuilder.struct().name("com.landoop.kafka.connect.twitter.decahose.Tweet")
    .field("id", Schema.INT64_SCHEMA)
    .field("created_at", Schema.STRING_SCHEMA)
    .field("user", TwitterUser.schema)
    .field("text", Schema.STRING_SCHEMA)
    .field("lang", Schema.STRING_SCHEMA)
    .field("is_retweet", Schema.BOOLEAN_SCHEMA)
    .field("entities", Entities.schema)
    .build()
}

