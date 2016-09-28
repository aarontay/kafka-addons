package com.landoop.kafka.connect.twitter.decahose

import java.net.{HttpURLConnection, URL, URLConnection}
import org.apache.kafka.connect.source.{SourceRecord, SourceTaskContext}
import com.landoop.kafka.connect.twitter.decahose.utils.Logging
import java.util.concurrent.LinkedBlockingQueue
import java.util.zip.GZIPInputStream
import scala.collection.JavaConversions._
import scala.concurrent.duration._
import sun.misc.BASE64Encoder
import java.util
import java.io._

class DecahoseReader(config: DecahoseConfig, context: SourceTaskContext) extends Logging {

  log.info("Initialising Twitter Decahose v1.0 Stream Reader")
  val messageQueue = new LinkedBlockingQueue[String](10000)

  val gnipAccount = GnipAccount()

  val downstream = new LinkedBlockingQueue[String](10000)
  val gzipedStreamingConnection = getStreamingConnection(gzip = true)
  val uncompressedStreamingConnection = getStreamingConnection(gzip = false)

  def getStreamingConnection(gzip: Boolean): URLConnection = {

    // Create an http connection
    val httpConnection = new URL(gnipAccount.getStreamingUrls).openConnection.asInstanceOf[HttpURLConnection]
    httpConnection.setReadTimeout(1.hour.toMillis.toInt)
    httpConnection.setConnectTimeout(10.seconds.toMillis.toInt)
    httpConnection.setRequestProperty("Authorization", createAuthHeader(gnipAccount.username, gnipAccount.password))
    // 50-150% improvement due to reduced bandwidth
    if (gzip) {
      log.info("Creating GZIP connection")
      httpConnection.setRequestProperty("Accept-Encoding", "gzip")
    } else
      log.info("Creating UNCOMPRESSED connection")

    // Create streaming connection and get response code
    val inputStream = getInputStream(httpConnection)
    val responseCode = httpConnection.getResponseCode

    if (responseCode >= 200 && responseCode <= 299) {
      log.info("Streaming endpoint response : " + responseCode)

      val bufferedReader =
        if (gzip)
          new BufferedReader(new InputStreamReader(new GZIPInputStream(inputStream), "UTF-8"))
        else
          new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))

      var counter = 0
      val startTime = System.nanoTime
      try {
        Stream.continually(bufferedReader readLine()).forall { line =>
          if (line == null)
            throw new EOFException("Connection has been reset")
          counter += 1
          // println(counter + " " + line)
          displayStatistics(counter, startTime)
          true
        }
      } catch {
        case eof: EOFException =>
          log.warn("Caught EOF exception")
      }

    }

    httpConnection
  }

  // When you disconnect & re-try too soon - you might get a 502 response
  def getInputStream(httpConnection: HttpURLConnection): InputStream = {
    try {
      httpConnection.getInputStream
    } catch {
      case ioe: IOException =>
        log.warn(ioe.toString)
        log.warn("Retrying in 500 msec")
        Thread.sleep(500)
        getInputStream(httpConnection)
    }
  }

  // Display some statistics on the screen
  def displayStatistics(counter: Int, startTime: Long) =
  if (counter % 1000 == 0) {
    val seconds = (System.nanoTime - startTime) / 1000000000
    log.info(s"Tweets / sec : ${counter / seconds}")
  }

  def createAuthHeader(username: String, password: String): String = {
    val authToken = (username + ":" + password).getBytes
    "Basic " + base64Encode(authToken)
  }

  def base64Encode(bytes: Array[Byte]): String =
    new BASE64Encoder()
      .encode(bytes)
      .replace("\n", "")
      .replace("\r", "")

  /**
    * Drain the queue
    *
    * @return A List of SourceRecords
    **/
  def poll(): util.List[SourceRecord] = {
    //    if (client.isDone) log.warn("Client connection closed unexpectedly: ", client.getExitEvent.getMessage) //TODO: what next?

    //    val l = new util.ArrayList[Status]()
    //    statusQueue.drainWithTimeoutTo(l, batchSize, (batchTimeout * 1E9).toLong, TimeUnit.NANOSECONDS)
    //    l.asScala.map(statusConverter.convert(_, topic)).asJava
    List[SourceRecord]()
  }

  /**
    * Stop the HBC client
    **/
  def stop() = {
    log.info("Stop Twitter client")
    //    client.stop()
  }


}

/*
    // batch size to take from the queue
    // val batchSize = config.getInt("TwitterSourceConfig.BATCH_SIZE")
    // val batchTimeout = config.getDouble("TwitterSourceConfig.BATCH_TIMEOUT")

    //class TwitterStatusReader(client: BasicClient,
//                          rawQueue: LinkedBlockingQueue[String],
//                          batchSize : Int,
//                          batchTimeout: Double,
//                          topic: String,
//                          statusConverter: StatusToSourceRecord = StatusToTwitterStatusStructure
//                         ) extends Logging {

 */