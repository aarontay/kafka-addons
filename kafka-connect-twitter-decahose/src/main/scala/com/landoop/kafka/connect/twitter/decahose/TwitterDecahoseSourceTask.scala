package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.utils.Logging
import org.apache.kafka.connect.source.{SourceRecord, SourceTask}
import java.util

class TwitterDecahoseSourceTask extends SourceTask with Logging {

  private var reader : Option[TwitterStatusReader] = _

  override def poll(): util.List[SourceRecord] = {
    require(reader.isDefined, "Twitter client not initialized!")
    reader.get.poll()
  }

  override def start(props: util.Map[String, String]): Unit = {
    val sourceConfig = new TwitterSourceConfig(props)
    reader = Some(TwitterReader(config = sourceConfig, context = context))
    println()
  }

  override def stop() = {
    reader.foreach(r=>r.stop())
  }
  override def version(): String = ""
}
