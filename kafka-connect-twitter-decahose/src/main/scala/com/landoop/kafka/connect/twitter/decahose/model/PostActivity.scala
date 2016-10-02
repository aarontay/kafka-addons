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
                        twitter_entities: TwitterEntities,
                        inReplyTo: Option[InReplyTo],
                        twitter_lang: String,
                        gnip: Option[Gnip],
                        twitter_filter_level: String) {
  require(verb == "post", "Error in PostActivity marshalling")
}
