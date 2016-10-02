package com.landoop.kafka.connect.twitter.decahose.model

case class Tweet(id: String,
                 objectType: String,
                 verb: String,
                 postedTime: String,
                 generator: Generator,
                 provider: Provider,
                 link: String, // i.e. "http:\/\/twitter.com\/hhusted\/statuses\/780843014214283265",
                 body: String, // i.e. "body": "@Cnvrswrld @KieferBobbie @Always_Trump No. Because they are blinded like everyone else by the globalist. Wake up.",
                 display_text_range: List[Int], // i.e. [39, 113]
                 actor: Actor,
                 `object`: Object,
                 inReplyTo: Option[InReplyTo],
                 twitter_lang: String, // i.e. "en"
                 retweetCount: Int,
                 gnip: Option[Gnip],
                 twitter_filter_level: String, // i.e. "low"
                 twitter_entities: TwitterEntities
                )

// An object can be a `person`
// if verb="share" it can be `activity`
case class Object(objectType: String, // i.e. "note"
                  id: String, // i.e. "object:search.twitter.com,2005:780843014214283265"
                  summary: String, // i.e. "@Cnvrswrld @KieferBobbie @Always_Trump No. Because they are blinded like everyone else by the globalist. Wake up."
                  link: String, // i.e. "http:\/\/twitter.com\/hhusted\/statuses\/780843014214283265"
                  postedTime: String // i.e. "2016-09-27T18:54:16.000Z"
                 )

case class Generator(displayName: String, link: String)

case class Provider(objectType: String, displayName: String, link: String)

case class InReplyTo(link: String)
