package com.landoop.kafka.connect.twitter.decahose.model

case class TwitterHashTag(text: String,
                          indices: List[Int])

case class TwitterSymbol(text: String,
                         indices: List[Int])

case class TwitterEntities(hashtags: List[TwitterHashTag],
                           urls: List[TwitterUrl],
                           user_mentions: List[UserMention],
                           symbols: List[TwitterSymbol])

case class TwitterUrl(url: String,
                      expanded_url: String,
                      display_url: String,
                      indices: List[Int])

case class UserMention(screen_name: String, // i.e. "Cnvrswrld"
                       name: String, // i.e. "Conversation World"
                       id: Long, // i.e. 757215072670416896
                       id_str: String, // i.e. "757215072670416896"
                       indices: List[Int]
                      )