package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.model.{PostActivity, ShareActivity}
import com.landoop.kafka.connect.twitter.decahose.support.StringUtils
import com.landoop.kafka.connect.twitter.decahose.utils.Json
import org.scalatest.{Matchers, WordSpec}
import scala.io.Source
import org.json4s._

class DecahoseJsonParsingSpec extends WordSpec with Matchers {

  implicit val formats = DefaultFormats

  "The Decahose model should" should {

    val tweetA = Source.fromURL(getClass.getResource("/tweet_Post.json")).getLines.mkString
    val tweetB = Source.fromURL(getClass.getResource("/tweet_Post_withLocation.json")).getLines.mkString
    val tweetC = Source.fromURL(getClass.getResource("/tweet_Share.json")).getLines.mkString

    "parse a Json `post` TWEET in case classes" in {
      val post = Json.fromJson[PostActivity](tweetA)
      // marshalling should work
      post shouldBe an[PostActivity]
      // marshalling - un-marshalling should result same size
      StringUtils.sameLength(post, tweetA) shouldBe true
    }

    "parse a Json `post` TWEET (with location) in case classes" in {
      val post = Json.fromJson[PostActivity](tweetB)
      // marshalling should work
      post shouldBe an[PostActivity]
      // marshalling - un-marshalling should result same size
      StringUtils.sameLength(post, tweetB) shouldBe true
    }

    "parse a Json `share` TWEET in case classes" in {
      val share = Json.fromJson[ShareActivity](tweetC)
      // marshalling should work
      share shouldBe an[ShareActivity]
      // marshalling - un-marshalling should result same size
      StringUtils.sameLength(share, tweetC) shouldBe true
    }

    "parse a 100MByte chuck of Decahose messages" ignore {
      val decahose: Iterator[String] = Source.fromURL("file:///opt/twitter/sample/decahose/decahose.json").getLines

      var countPosts = 0
      var countShare = 0
      val list = decahose.flatMap { tweet =>
        if (tweet.contains("\"verb\":\"share\"")) {
          countShare += 1
          println("Parsing line : " + (countPosts + countShare) + " (share)")
          val share = Json.fromJson[ShareActivity](tweet)
          StringUtils.sameLength(share, tweet) shouldBe true
          Some(share)
        } else if (tweet.contains("\"verb\":\"post\"")) {
          countPosts += 1
          println("Parsing line : " + (countPosts + countShare) + " (post)")
          val result = Json.fromJson[PostActivity](tweet)
          Some(result)
        }
        else {
          throw new Exception("Unrecognized json line: " + tweet)
          None
        }
      }
      println(list.length)
    }
  }

}
