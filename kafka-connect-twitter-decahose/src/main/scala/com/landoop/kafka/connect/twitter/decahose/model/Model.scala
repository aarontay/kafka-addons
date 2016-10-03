package com.landoop.kafka.connect.twitter.decahose.model

case class Generator(displayName: String, link: String)

case class Provider(objectType: String, displayName: String, link: String)

case class InReplyTo(link: String)

case class NoteObject(objectType: String,
                      id: String,
                      summary: String,
                      link: String,
                      postedTime: String) {
  // i.e. "note"
}

case class Actor(objectType: String,
                 id: String,
                 link: String,
                 displayName: String,
                 postedTime: String,
                 image: String,
                 summary: String,

                 friendsCount: Long,
                 followersCount: Long,
                 listedCount: Long,
                 statusesCount: Long,

                 twitterTimeZone: String,
                 verified: Boolean,
                 utcOffset: String,
                 preferredUsername: String,
                 languages: List[String],
                 links: List[Link],
                 location: Option[Location],
                 favoritesCount: Long
                ) {
  require(objectType == "person", "Error marshalling Actor")
}

case class Link(href: String, rel: String)

case class Location(objectType: String, displayName: String) {
  require(objectType == "place", "Error marshalling Location")
}

/* -------- Gnip -------- */
case class Gnip(urls: Option[List[GnipUrl]],
                profileLocations: List[GnipProfileLocation])

case class GnipUrl(url: Option[String],
                   expanded_url: String,
                   expanded_status: Int,
                   expanded_url_title: String,
                   expanded_url_description: String)

case class GnipProfileLocation(address: GnipAddress,
                               displayName: String,
                               geo: GnipGeo,
                               objectType: String)

case class GnipAddress(country: String, countryCode: String, region: Option[String])

case class GnipGeo(coordinates: List[Double],
                   `type`: String)

/* -------- TwitterEntities -------- */
case class TwitterHashTag(text: String,
                          indices: List[Int])

case class TwitterSymbol(text: String,
                         indices: List[Int])

case class TwitterEntities(hashtags: List[TwitterHashTag],
                           urls: List[TwitterUrl],
                           user_mentions: List[UserMention],
                           symbols: List[TwitterSymbol],
                           media: Option[List[TwitterExtendedMedia]])

case class TwitterExtendedEntities(media: List[TwitterExtendedMedia])

case class TwitterExtendedMedia(id: Long,
                                id_str: String,
                                indices: List[Int],
                                media_url: String,
                                media_url_https: String,
                                url: String,
                                display_url: String,
                                expanded_url: String,
                                `type`: String,
                                sizes: MediaSizes,
                                source_status_id: Option[Long],
                                source_status_id_str: Option[String],
                                source_user_id: Option[Long],
                                source_user_id_str: Option[String])

case class MediaSizes(thumb: MediaInfo,
                      medium: MediaInfo,
                      large: MediaInfo,
                      small: MediaInfo)

case class MediaInfo(w: Int,
                     h: Int,
                     resize: String)

case class TwitterUrl(url: String,
                      expanded_url: String,
                      display_url: String,
                      indices: List[Int])

case class UserMention(screen_name: String,
                       name: String,
                       id: Long,
                       id_str: String,
                       indices: List[Int])
