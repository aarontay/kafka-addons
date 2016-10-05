package com.landoop.kafka.connect.twitter.decahose.experiments

import com.landoop.kafka.connect.twitter.decahose.utils.Logging
import com.landoop.kafka.connect.twitter.decahose.GnipAccount
import java.util.concurrent.LinkedBlockingQueue
import java.net.{HttpURLConnection, URL}
import java.util.zip.GZIPInputStream
import scala.concurrent.duration._
import sun.misc.BASE64Encoder
import java.io._

/**
  * A simple experiment to build an efficient Decahose client with minimum dependencies
  */
object ConsumeDecahoseApp extends App with Logging {

  val gnipAccount = GnipAccount()

  val downstream = new LinkedBlockingQueue[String](100000)
  // val uncompressedStreamingConnection = getStreamingConnection(gzip = false)
  val gzipedStreamingConnection = getStreamingConnection(gzip = true)

  gzipedStreamingConnection match {
    case Some(reader) =>
      var counter = 0
      val startTime = System.nanoTime
      try {
        Stream.continually(reader readLine()).forall { line =>
          if (line == null) throw new EOFException("Connection has been reset")
          counter += 1
          downstream.add(line)
          displayStatistics(counter, startTime)
          true
        }
      } catch {
        case eof: EOFException =>
          log.warn("Caught EOF exception")
      }
    case None =>
  }

  def getStreamingConnection(gzip: Boolean): Option[BufferedReader] = {

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

      Option(bufferedReader)
    } else
      None

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
  def displayStatistics(counter: Int, startTime: Long) = if (counter % 1000 == 0) log.info(s"Tweets / sec :" + counter / ((System.nanoTime - startTime) / 1000000000))

  def createAuthHeader(username: String, password: String): String = "Basic " + base64Encode((username + ":" + password).getBytes)

  def base64Encode(bytes: Array[Byte]): String =
    new BASE64Encoder()
      .encode(bytes)
      .replace("\n", "")
      .replace("\r", "")

  def setBackfillCount(count: Int) = {
  }

}
