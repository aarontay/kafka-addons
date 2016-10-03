package com.landoop.kafka.connect.twitter.decahose.support

import com.landoop.kafka.connect.twitter.decahose.model.{PostActivity, ShareActivity}
import org.json4s.jackson.Serialization.write
import org.json4s.DefaultFormats

object StringUtils {

  implicit val formats = DefaultFormats

  def unescapeUnicode(str: String): String =
    """\\u([0-9a-fA-F]{4})""".r.replaceAllIn(str,
      m => Integer.parseInt(m.group(1), 16).toChar.toString)

  def sameLength(postActivity: PostActivity, tweet: String) = {
    val toJson = write(postActivity).replace(" ", "")
    val originalTweet = unescapeUnicode(tweet.replace("\\/", "/").replace(" ", ""))
    val same = toJson.length == originalTweet.length
    if (!same) {
      println("Original")
      println(originalTweet)
      println("Marshaled & Un-marshaled")
      println(toJson)
    }
    same
  }

  def sameLength(shareActivity: ShareActivity, tweet: String) = {
    val toJson = unescapeUnicode(write(shareActivity)).replace(" ", "")
    val originalTweet = unescapeUnicode(tweet.replace("\\/", "/").replace(" ", ""))
    val difference = originalTweet.length - toJson.length
    // We allow up to 4 chars diff as sometimes a Long co-ordinate i.e. 102.764270 will become 102.76427
    val same = (difference >=0) && (difference <= 4)
    if (!same) {
      println("Original " + difference)
      println(originalTweet)
      println("Marshaled & Un-marshaled")
      println(toJson)
    }
    same
  }

}
