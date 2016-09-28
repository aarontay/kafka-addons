//package com.landoop.kafka.connect.twitter.decahose
//
//import java.util.concurrent.LinkedBlockingQueue
//import org.apache.kafka.connect.data.Schema
//import org.apache.kafka.connect.source.SourceRecord
//import twitter4j._
//
//import scala.collection.JavaConverters._
//import com.landoop.kafka.connect.twitter.decahose.model.TwitterStatus
//import com.landoop.kafka.connect.twitter.decahose.utils.Logging
//
//class StatusEnqueuer(queue: LinkedBlockingQueue[Status]) extends StatusListener with Logging {
//  override def onStallWarning(stallWarning: StallWarning) = log.warn("onStallWarning")
//  override def onDeletionNotice(statusDeletionNotice: StatusDeletionNotice) = log.info("onDeletionNotice")
//
//  override def onScrubGeo(l: Long, l1: Long) = {
//    log.debug(s"onScrubGeo $l $l1")
//  }
//
//  override def onStatus(status: Status) = {
//    log.debug("onStatus")
//    queue.put(status)
//  }
//
//  override def onTrackLimitationNotice(i: Int) = log.info(s"onTrackLimitationNotice $i")
//  override def onException(e: Exception)= log.warn("onException " + e.toString)
//}
//
//trait StatusToSourceRecord {
//  def convert(status: Status, topic: String): SourceRecord
//}
//
//object StatusToStringKeyValue extends StatusToSourceRecord {
//  def convert (status: Status, topic: String): SourceRecord = {
//    new SourceRecord(
//      Map("tweetSource" -> status.getSource).asJava, //source partitions?
//      Map("tweetId" -> status.getId).asJava, //source offsets?
//      topic,
//      null,
//      Schema.STRING_SCHEMA,
//      status.getUser.getScreenName,
//      Schema.STRING_SCHEMA,
//      status.getText)
//  }
//}
//
//object StatusToTwitterStatusStructure extends StatusToSourceRecord {
//  def convert(status: Status, topic: String): SourceRecord = {
//    //val ts = TwitterStatus.struct(TwitterStatus(status))
//    new SourceRecord(
//      Map("tweetSource" -> status.getSource).asJava, //source partitions?
//      Map("tweetId" -> status.getId).asJava, //source offsets?
//      topic,
//      TwitterStatus.schema,
//      TwitterStatus.struct(status))
//  }
//}
