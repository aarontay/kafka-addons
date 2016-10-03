package com.landoop.kafka.connect.twitter.decahose.model

case class PostActivity(id: String,
                        objectType: String,
                        verb: String,
                        postedTime: String,
                        generator: Generator,
                        provider: Provider,
                        link: String,
                        body: String,
                        display_text_range: List[Int],
                        actor: Actor,
                        `object`: NoteObject,
                        inReplyTo: Option[InReplyTo],
                        favoritesCount: Int,
                        twitter_entities: TwitterEntities,
                        twitter_extended_entities: Option[TwitterExtendedEntities],
                        twitter_lang: String,
                        retweetCount: Option[Int], // Only pure posts have a retweetCount - otherwise on share
                        gnip: Option[Gnip],
                        twitter_filter_level: String) {
  require(verb == "post", "Error in PostActivity marshalling")
}
