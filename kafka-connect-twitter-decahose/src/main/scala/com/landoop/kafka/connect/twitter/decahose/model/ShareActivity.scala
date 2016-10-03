package com.landoop.kafka.connect.twitter.decahose.model

case class ShareActivity(id: String,
                         objectType: String,
                         verb: String,
                         postedTime: String,
                         generator: Generator,
                         provider: Provider,
                         link: String,
                         body: String,
                         display_text_range: Option[List[Int]], // Only when verb=post
                         actor: Actor,
                         `object`: PostActivity,
                         favoritesCount: Int,
                         twitter_entities: TwitterEntities,
                         twitter_extended_entities: Option[TwitterExtendedEntities],
                         //inReplyTo: Option[InReplyTo], // Only when verb=post
                         twitter_lang: String,
                         retweetCount: Int,
                         gnip: Option[Gnip],
                         twitter_filter_level: String,
                         twitter_quoted_status: Option[PostActivity]) {
  require(verb == "share", "Error in ShareActivity marshalling")
}
