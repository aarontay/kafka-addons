package com.landoop.kafka.connect.twitter.decahose

import java.util.concurrent.LinkedBlockingQueue

import com.twitter.hbc.ClientBuilder
import com.twitter.hbc.core.{Constants, HttpHosts}
import com.twitter.hbc.core.endpoint._
import com.twitter.hbc.core.processor.{LineStringProcessor, StringDelimitedProcessor}
import com.twitter.hbc.httpclient.auth.{BasicAuth, OAuth1}
import org.apache.kafka.connect.source.{SourceRecord, SourceTaskContext}
import twitter4j.Status
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

object TwitterReader {

  def apply(config: TwitterSourceConfig, context: SourceTaskContext) = {

    def run(username: String, password: String, account: String, label: String, product: String) = {
      val queue = new LinkedBlockingQueue[String](10000)
      val auth = new BasicAuth(username, password)

      val endpoint = new RealTimeEnterpriseStreamingEndpoint(account, product, label)

      // Create a new BasicClient. By default gzip is enabled.
      val client = new ClientBuilder()
        .name("PowerTrackClient-01")
        .hosts(Constants.ENTERPRISE_STREAM_HOST)
        .endpoint(endpoint)
        .authentication(auth)
        .processor(new LineStringProcessor(queue))
        .build

      // Establish a connection
      client.connect()

      // Do whatever needs to be done with messages
      for (msgRead <- 0 to 1000) {
        val msg = queue.take()
        println(msg)
      }

      client.stop()

      //endpoints
      //val endpoint: DefaultStreamingEndpoint = //if (config.getString(TwitterSourceConfig.STREAM_TYPE).equals(TwitterSourceConfig.STREAM_TYPE_SAMPLE)) {
      //  new StatusesSampleEndpoint()
      /*
      } else {
        val trackEndpoint = new StatusesFilterEndpoint()
        val terms = config.getList(TwitterSourceConfig.TRACK_TERMS)
        if (!terms.isEmpty) {
          trackEndpoint.trackTerms(terms)
        }
        val locs = config.getList(TwitterSourceConfig.TRACK_LOCATIONS)
        if (!locs.isEmpty) {
          val locations = locs.toList.map({ x => Double.box(x.toDouble)}).grouped(4).toList
              .map({ l => new Location(new Location.Coordinate(l(0), l(1)), new Location.Coordinate(l(2), l(3)))})
              .asJava
          trackEndpoint.locations(locations)
        }
        val follow = config.getList(TwitterSourceConfig.TRACK_FOLLOW)
        if (!follow.isEmpty) {
          val users = follow.toList.map({ x => Long.box(x.trim.toLong)}).asJava
          trackEndpoint.followings(users)
        }
        trackEndpoint
      }

      endpoint.stallWarnings(false)
      val language = config.getList(TwitterSourceConfig.LANGUAGE)
      if (!language.isEmpty) {
        // endpoint.languages(language) doesn't work as intended!
        endpoint.addQueryParameter(TwitterSourceConfig.LANGUAGE, language.toList.mkString(","))
      }

      //twitter auth stuff
      val auth = new OAuth1(config.getString(TwitterSourceConfig.TWITTER_USERNAME),
        config.getPassword(TwitterSourceConfig.TWITTER_PASSWORD).value,
        config.getString(TwitterSourceConfig.TWITTER_URL),"")
        //config.getPassword(TwitterSourceConfig.SECRET_CONFIG).value)

      //batch size to take from the queue
      val batchSize = config.getInt(TwitterSourceConfig.BATCH_SIZE)
      val batchTimeout = config.getDouble(TwitterSourceConfig.BATCH_TIMEOUT)

      //The Kafka topic to append to
      val topic = config.getString(TwitterSourceConfig.TOPIC)

      //queue for client to buffer to
      val queue = new LinkedBlockingQueue[String](10000)

      //how the output is formatted
      val statusConverter = config.getString(TwitterSourceConfig.OUTPUT_FORMAT) match {
        case TwitterSourceConfig.OUTPUT_FORMAT_ENUM_STRING => StatusToStringKeyValue
        case TwitterSourceConfig.OUTPUT_FORMAT_ENUM_STRUCTURED => StatusToTwitterStatusStructure
      }

      //build basic client
      val client = new ClientBuilder()
        .name(config.getString(TwitterSourceConfig.TWITTER_APP_NAME))
        .hosts(Constants.STREAM_HOST)
        .endpoint(endpoint)
        .authentication(auth)
        .processor(new StringDelimitedProcessor(queue))
        .build()

      new TwitterStatusReader(client = client, rawQueue = queue, batchSize = batchSize,
          batchTimeout = batchTimeout, topic = topic, statusConverter = statusConverter)

      */
    }
  }
