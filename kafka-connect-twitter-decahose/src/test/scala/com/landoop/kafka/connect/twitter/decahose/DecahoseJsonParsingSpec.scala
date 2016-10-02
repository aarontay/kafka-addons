package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.model.{PostActivity, ShareActivity}
import com.landoop.kafka.connect.twitter.decahose.utils.Json
import org.scalatest.{Matchers, WordSpec}

import scala.io.Source

class DecahoseJsonParsingSpec extends WordSpec with Matchers {

  "The Decahose model should" should {

    val tweetA = Source.fromURL(getClass.getResource("/tweet_Post.json")).getLines.mkString
    val tweetB = Source.fromURL(getClass.getResource("/tweet_Post_withLocation.json")).getLines.mkString
    val tweetC = Source.fromURL(getClass.getResource("/tweet_Share.json")).getLines.mkString

    "parse a Json `post` TWEET in case classes" in {
      val msg = Json.fromJson[PostActivity](tweetA)
      msg shouldBe an[PostActivity]
    }

    "parse a Json `post` TWEET (with location) in case classes" in {
      val msg = Json.fromJson[PostActivity](tweetB)
      msg shouldBe an[PostActivity]
    }

    "parse a Json `share` TWEET in case classes" in {
      val msg = Json.fromJson[ShareActivity](tweetC)
      msg shouldBe an[ShareActivity]
    }

    "parse a bigger chuck of Decahose messages" in {
      val decahose: Iterator[String] = Source.fromURL("file:///tmp/twitter/sample/json/decahose.json").getLines

      var countPosts = 0
      var countShare = 0
      val list = decahose.flatMap { tweet =>
        if (tweet.contains("\"verb\":\"share\"")) {
          countShare += 1
          println("Parsing line : " + (countPosts + countShare) + " (share)")
          Some(Json.fromJson[ShareActivity](tweet))
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
