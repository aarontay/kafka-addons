package com.landoop.kafka.connect.twitter.decahose

import java.util
import java.util.concurrent.{Executors, LinkedBlockingQueue, TimeUnit}
import org.apache.kafka.connect.source.{SourceRecord, SourceTaskContext}
import com.landoop.kafka.connect.twitter.decahose.utils.Logging
import com.twitter.hbc.httpclient.BasicClient
import com.twitter.hbc.twitter4j.Twitter4jStatusClient
import sun.security.x509.Extension
import twitter4j.{Status, StatusListener}

import scala.collection.JavaConverters._
import scala.collection.JavaConversions._

//class TwitterStatusReader(client: BasicClient,
//                          rawQueue: LinkedBlockingQueue[String],
//                          batchSize : Int,
//                          batchTimeout: Double,
//                          topic: String,
//                          statusConverter: StatusToSourceRecord = StatusToTwitterStatusStructure
//                         ) extends Logging {
class DecahoseReader(config: DecahoseConfig, context: SourceTaskContext) extends Logging {

  log.info("Initialising Twitter Stream Reader")
  val statusQueue = new LinkedBlockingQueue[Status](10000)
  import com.landoop.kafka.connect.twitter.decahose.utils.Extensions._

  //Construct the status client
//  val t4jClient = new Twitter4jStatusClient(
//    client,
//    rawQueue,
//    List[StatusListener](new StatusEnqueuer(statusQueue)).asJava,
//    Executors.newFixedThreadPool(1))

  //connect and subscribe
//  t4jClient.connect()
//  t4jClient.process()

  /**
    * Drain the queue
    *
    * @return A List of SourceRecords
    * */
  def poll() : util.List[SourceRecord] = {
//    if (client.isDone) log.warn("Client connection closed unexpectedly: ", client.getExitEvent.getMessage) //TODO: what next?

//    val l = new util.ArrayList[Status]()
//    statusQueue.drainWithTimeoutTo(l, batchSize, (batchTimeout * 1E9).toLong, TimeUnit.NANOSECONDS)
//    l.asScala.map(statusConverter.convert(_, topic)).asJava
    List[SourceRecord]()
  }

  /**
    * Stop the HBC client
    * */
  def stop() = {
    log.info("Stop Twitter client")
//    client.stop()
  }


}
/*
    // batch size to take from the queue
    // val batchSize = config.getInt("TwitterSourceConfig.BATCH_SIZE")
    // val batchTimeout = config.getDouble("TwitterSourceConfig.BATCH_TIMEOUT")
 */