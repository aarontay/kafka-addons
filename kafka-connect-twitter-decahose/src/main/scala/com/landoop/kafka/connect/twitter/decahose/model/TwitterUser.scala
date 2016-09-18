package com.landoop.kafka.connect.twitter.decahose.model

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

object TwitterUser {

  val schema = SchemaBuilder.struct().name("com.landoop.kafka.connect.twitter.decahose.User")
    .field("id", Schema.INT64_SCHEMA)
    .field("name", Schema.STRING_SCHEMA)
    .field("screen_name", Schema.STRING_SCHEMA)
    .field("location", SchemaBuilder.string.optional.build())
    .field("verified", Schema.BOOLEAN_SCHEMA)
    .field("friends_count", Schema.INT32_SCHEMA)
    .field("followers_count", Schema.INT32_SCHEMA)
    .field("statuses_count", Schema.INT32_SCHEMA)
    .build()

  def struct(u: twitter4j.User) =
    new Struct(schema)
      .put("id", u.getId)
      .put("name", u.getName)
      .put("screen_name", u.getScreenName)
      .put("location", u.getLocation)
      .put("verified", u.isVerified)
      .put("friends_count", u.getFriendsCount)
      .put("followers_count", u.getFollowersCount)
      .put("statuses_count", u.getStatusesCount)

}
