package com.landoop.kafka.connect.twitter.decahose.model

// @formatter:off
case class Actor(objectType        : String, // i.e. "person"
                 id                : String, // i.e. "id:twitter.com:123"
                 link              : String, // i.e. "http:\/\/www.twitter.com\/123"
                 displayName       : String, // i.e. "my   name"
                 postedTime        : String, // i.e. "2008-02-07T07:07:49.000Z"
                 image             : String, // i.e. "https:\/\/pbs.twimg.com\/profile_images\/123\/harryphoto_normal.jpg"
                 summary           : String, // i.e. "I'm a full-time writer, part-time screenwriter, instant storyteller, and author."

                 friendsCount      : Long, // i.e. 1227
                 followersCount    : Long, // i.e. 891
                 listedCount       : Long, // i.e. 14
                 statusesCount     : Long, // i.e. 2090

                 twitterTimeZone   : String, // i.e.  "Eastern Time (US & Canada)"
                 verified          : Boolean, // i.e. false
                 utcOffset         : String, // i.e. "-14400"
                 preferredUsername : String, // i.e. "myusername"
                 languages         : List[String], // i.e. ["en"]
                 links             : List[Link],
                 location          : Option[Location],
                 favoritesCount    : Long
                )

case class Link(href: String, rel: String) // i.e. "http:\/\/somesite.com" , "me"

case class Location(objectType: String, displayName: String) // i.e. "place" , "NYC presently"
