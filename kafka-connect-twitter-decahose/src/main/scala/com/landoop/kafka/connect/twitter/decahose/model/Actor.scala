package com.landoop.kafka.connect.twitter.decahose.model

import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

// @formatter:off
case class Actor(objectType        : String, // i.e. "person"
                 id                : String, // i.e. "id:twitter.com:13195232"
                 link              : String, // i.e. "http:\/\/www.twitter.com\/hhusted"
                 displayName       : String, // i.e. "Deplorable  Harry"
                 preferredUsername : String, // i.e. "hhusted"
                 postedTime        : String, // i.e. "2008-02-07T07:07:49.000Z"
                 image             : String, // i.e. "https:\/\/pbs.twimg.com\/profile_images\/506655096\/harryphoto_normal.jpg"
                 summary           : String, // i.e. "I'm a full-time writer, part-time screenwriter, instant storyteller, and author."
                 location          : String,
                 utcOffset         : String, // i.e. "-14400"
                 twitterTimeZone   : String, // i.e.  "Eastern Time (US & Canada)"

                 friendsCount      : Long, // i.e. 1227
                 followersCount    : Long, // i.e. 891
                 listedCount       : Long, // i.e. 14
                 statusesCount     : Long, // i.e. 2090
                 favoritesCount    : Long, // i.e. 4 (OPTIONAL ?)

                 verified          : Boolean, // i.e. false
                 languages         : List[String], // i.e. ["en"]
                 links             : List[Link])

object Actor {

  val ConnectSchema = SchemaBuilder.struct
    .name("streamreactor.twitter.decahose.tweet.actor")
    .doc("The actor that generated the tweet event")
    .field("objectType",        Schema.STRING_SCHEMA)
    .field("id",                Schema.STRING_SCHEMA)
    .field("link",              Schema.STRING_SCHEMA)
    .field("displayName",       Schema.STRING_SCHEMA)
    .field("preferredUsername", Schema.STRING_SCHEMA)
    .field("postedTime",        Schema.STRING_SCHEMA)
    .field("image",             Schema.STRING_SCHEMA)
    .field("summary",           Schema.STRING_SCHEMA)
    .field("location",          Schema.STRING_SCHEMA)
    .field("utcOffset",         Schema.STRING_SCHEMA)
    .field("twitterTimeZone",   Schema.STRING_SCHEMA)

    .field("friendsCount",      Schema.INT16_SCHEMA)
    .field("followersCount",    Schema.INT16_SCHEMA)
    .field("listedCount",       Schema.INT16_SCHEMA)
    .field("statusesCount",     Schema.INT16_SCHEMA)
    .field("favoritesCount",    Schema.INT16_SCHEMA)

    .field("verified",          Schema.BOOLEAN_SCHEMA)
    .field("languages",         SchemaBuilder.array(Schema.STRING_SCHEMA).build)
    .field("links",             SchemaBuilder.array(Schema.STRING_SCHEMA).build) // TODO
    .build()

  implicit class ActorToStructConverter(val actor: Actor) extends AnyVal {
    def toStruct: Struct =
      new Struct(ConnectSchema)
        .put("objectType",        actor.objectType)
        .put("link",              actor.link)
        .put("displayName",       actor.displayName)
        .put("preferredUsername", actor.preferredUsername)
        .put("postedTime",        actor.postedTime)
        .put("image",             actor.image)
        .put("summary",           actor.summary)
        .put("location",          actor.location)
        .put("utcOffset",         actor.utcOffset)
        .put("twitterTimeZone",   actor.twitterTimeZone)

        .put("friendsCount",      actor.friendsCount)
        .put("followersCount",    actor.followersCount)
        .put("listedCount",       actor.listedCount)
        .put("statusesCount",     actor.statusesCount)
        .put("favoritesCount",    actor.favoritesCount)

        .put("verified",          actor.verified)
        .put("languages",         actor.languages)
        .put("links",             actor.links)
  }

}


case class Link(href: String, // i.e. "http:\/\/creatingwords.com"
                rel: String // i.e. "me"
               )

case class Location(objectType: String, // i.e. "place"
                    displayName: String // i.e. "NYC presently"
                   )

/**
object Implicits {
  implicit class CaseClassToString(c: AnyRef) {
    def toStringWithFields: String = {
      val fields = (Map[String, Any]() /: c.getClass.getDeclaredFields) { (a, f) =>
        f.setAccessible(true)
        a + (f.getName -> f.get(c))
      }

      s"${c.getClass.getName}(${fields.mkString(", ")})"
    }
  }
}

case class PriceMove(price: Double, delta: Double)

object Test extends App {
  import Implicits._
  println(PriceMove(1.23, 2.56).toStringWithFields)
}
**/