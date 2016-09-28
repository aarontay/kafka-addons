package com.landoop.kafka.connect.twitter.decahose.model

case class TwitterEntities(hashtags: List[String],
                           urls: List[String],
                           symbols: List[String],
                           user_mentions: List[UserMention])

case class UserMention(screen_name: String, // i.e. "Cnvrswrld"
                       name: String, // i.e. "Conversation World"
                       id: Long, // i.e. 757215072670416896
                       id_str: String, // i.e. "757215072670416896"
                       indices: List[Int]
                      )