package com.landoop.kafka.connect.twitter.decahose

import org.apache.kafka.common.config.{AbstractConfig, ConfigDef}
import org.apache.kafka.common.config.ConfigDef.{Type, Importance}
import java.util

object TwitterSourceConfig {

  val TWITTER_USERNAME = "twitter.username"
  val TWITTER_USERNAME_DOC = "Username at Gnip"

  val TWITTER_PASSWORD = "twitter.password"
  val TWITTER_PASSWORD_DOC = "Password at Gnip"

  val TWITTER_URL = "twitter.url"
  val TWITTER_URL_DOC = "Twitter url. If multiple urls are used, pass a comma-separated list"


  val TWITTER_APP_NAME = "twitter.app.name"
  val TWITTER_APP_NAME_DOC = "Twitter app name"
  val TWITTER_APP_NAME_DEFAULT = "KafkaConnectTwitterDecahoseSource"

  val TOPIC = "topic"
  val TOPIC_DOC = "The Kafka topic to append to"
  val TOPIC_DEFAULT = "tweets"

  //  val LANGUAGE = "language"
  //  val LANGUAGE_DOC = "List of languages to filter"
  //  val OUTPUT_FORMAT = "output.format"
  //  val OUTPUT_FORMAT_ENUM_STRUCTURED = "structured"
  //  val OUTPUT_FORMAT_ENUM_STRING = "string"
  //  val OUTPUT_FORMAT_DOC = s"How the output is formatted, can be either $OUTPUT_FORMAT_ENUM_STRING for (key=username:string, value=text:string), or ${OUTPUT_FORMAT_ENUM_STRUCTURED} for value=structure:TwitterStatus."
  //  val OUTPUT_FORMAT_DEFAULT = "structured"
  //  val EMPTY_VALUE = ""

  val config: ConfigDef = new ConfigDef()
    .define(TWITTER_USERNAME, Type.STRING, Importance.HIGH, TWITTER_USERNAME_DOC)
    .define(TWITTER_PASSWORD, Type.PASSWORD, Importance.HIGH, TWITTER_PASSWORD_DOC)
    .define(TWITTER_URL, Type.STRING, Importance.HIGH, TWITTER_URL_DOC)
    .define(TWITTER_APP_NAME, Type.STRING, TWITTER_APP_NAME_DEFAULT, Importance.HIGH, TWITTER_APP_NAME_DOC)
    .define(TOPIC, Type.STRING, TOPIC_DEFAULT, Importance.HIGH, TOPIC_DOC)
}

class TwitterSourceConfig(props: util.Map[String, String])
  extends AbstractConfig(TwitterSourceConfig.config, props) {
  val terms = List() //getList(TwitterSourceConfig.TRACK_TERMS)
  //  val locations = getList(TwitterSourceConfig.TRACK_LOCATIONS)
  //  val users = getList(TwitterSourceConfig.TRACK_FOLLOW)
  //  val language = getList(TwitterSourceConfig.LANGUAGE)
  //  if (terms.isEmpty && locations.isEmpty && users.isEmpty) {
  //    throw new RuntimeException("At least one of the parameters : track-terms "
  //      + ", " + TwitterSourceConfig.TRACK_FOLLOW + " should be specified!")
  //  }
  /*
  if (!locations.isEmpty) {
    if ((locations.size % 4) != 0) {
      throw new RuntimeException(TwitterSourceConfig.TRACK_LOCATIONS
        + " should have number of elements divisible by 4!")
    }
    try {
      locations.toList.map { x => x.trim.toDouble }
    } catch {
      case e: NumberFormatException => throw new RuntimeException("You should use double numbers in "
        + TwitterSourceConfig.TRACK_LOCATIONS)
    }
  }

  try {
    users.toList.map { x => x.trim.toLong }
  } catch {
    case e: NumberFormatException => throw new RuntimeException("You should use numeric user IDs in "
      + TwitterSourceConfig.TRACK_FOLLOW)
  }
  */
}
