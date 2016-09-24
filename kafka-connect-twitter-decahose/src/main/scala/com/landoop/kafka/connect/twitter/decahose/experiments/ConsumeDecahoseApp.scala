package com.landoop.kafka.connect.twitter.decahose.experiments

import com.landoop.kafka.connect.twitter.decahose.model.Account
import java.io.{BufferedReader, InputStreamReader}
import java.util.concurrent.LinkedBlockingQueue
import java.net.{HttpURLConnection, URL, URLConnection}
import java.util.zip.GZIPInputStream
import scala.concurrent.duration._
import sun.misc.BASE64Encoder

/**
  * A simple experiments to build an efficient Decahose client without any external dependencies
  */
object ConsumeDecahoseApp extends App {

  val gnipAccount = Account()

  val downstream = new LinkedBlockingQueue[String](10000)
  val gzipedStreamingConnection = getStreamingConnection(gzip = true)

  // () throws IOException ?
  def getStreamingConnection(gzip: Boolean): URLConnection = {

    // Create an http connection
    val httpConnection = new URL(gnipAccount.getStreamingUrls).openConnection.asInstanceOf[HttpURLConnection]
    httpConnection.setReadTimeout(1.hour.toMillis.toInt)
    httpConnection.setConnectTimeout(10.seconds.toMillis.toInt)
    httpConnection.setRequestProperty("Authorization", createAuthHeader(gnipAccount.username, gnipAccount.password))
    // 50-60% improvement due to reduced bandwidth
    if (gzip)
      httpConnection.setRequestProperty("Accept-Encoding", "gzip")

    // Create streaming connection and get response code
    val inputStream = httpConnection.getInputStream
    val responseCode = httpConnection.getResponseCode

    if (responseCode >= 200 && responseCode <= 299) {
      println("Streaming endpoint response : " + responseCode)

      val bufferedReader =
        if (gzip)
          new BufferedReader(new InputStreamReader(new GZIPInputStream(inputStream), "UTF-8"))
        else
          new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))

      var counter = 0
      val startTime = System.nanoTime
      val input: Stream[String] = Stream.continually(bufferedReader readLine())
      input.forall { line =>
        counter += 1
        //println(counter + " " + line)
        displayStatistics(counter, startTime)
        true
      }

    }

    httpConnection
  }

  def displayStatistics(counter: Int, startTime: Long) =
    if (counter % 1000 == 0) {
      val seconds = (System.nanoTime - startTime) / 1000000000
      println(s"$seconds sec to fetch [$counter] tweets")
      println(s"Tweets / sec : ${counter / seconds}")
    }

  def createAuthHeader(username: String, password: String): String = {
    val authToken = (username + ":" + password).getBytes
    //"Basic " + base64Encode(authToken)
    "Basic " + twitter4j.BASE64Encoder.encode(authToken)
  }

  def base64Encode(bytes: Array[Byte]): String =
    new BASE64Encoder()
      .encode(bytes)
      .replace("\n", "")
      .replace("\r", "")

  def setBackfillCount(count: Int) = {}

}

/*

  // NEW LineStringProcessor to handle Gnip formatted streaming HTTP
  //  val processor = new LineStringProcessor(downstream)
  //  val hostBirdClient = new ClientBuilder()
  //    .name("Connection Name")
  //    .hosts("https://gnip-stream.twitter.com") // Constants.ENTERPRISE_STREAM_HOST // Declared in HBC Constants
  //    .endpoint(endpoint)
  //    .authentication(auth)
  //    .processor(processor)
  //    .build()

  // connect and process messages - just like we do with the public API
  //  hostBirdClient.connect()
  //  while (!hostBirdClient.isDone) {
  //    var message = ""
  //    try {
  //      message = downstream.take()
  //    } catch {
  //      case e: InterruptedException => e.printStackTrace()
  //    }
  //    println(message); // Here is where you could put the payload on a queue for another thread to come in and take care of the message
  //  }

 */
