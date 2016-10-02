package com.landoop.kafka.connect.twitter.decahose.avro

import com.landoop.kafka.connect.twitter.decahose.model.Actor
import org.apache.kafka.connect.data.{Schema, SchemaBuilder, Struct}

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
    .field("location",          SchemaBuilder.array(Location.ConnectSchema).optional.build)
    .field("utcOffset",         Schema.STRING_SCHEMA)
    .field("twitterTimeZone",   Schema.STRING_SCHEMA)

    .field("friendsCount",      Schema.INT16_SCHEMA)
    .field("followersCount",    Schema.INT16_SCHEMA)
    .field("listedCount",       Schema.INT16_SCHEMA)
    .field("statusesCount",     Schema.INT16_SCHEMA)
    .field("favoritesCount",    Schema.INT16_SCHEMA)

    .field("verified",          Schema.BOOLEAN_SCHEMA)
    .field("languages",         SchemaBuilder.array(Schema.STRING_SCHEMA).build)
    .field("links",             SchemaBuilder.array(Link.ConnectSchema).optional.build)
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
