package com.landoop.kafka.connect.twitter.decahose

import com.landoop.kafka.connect.twitter.decahose.utils.Logging
import org.apache.kafka.connect.source.{SourceRecord, SourceTask}
import java.util

// Decahose Kafka Connect 'Source' Task
class DecahoseTask extends SourceTask with Logging {

  private var reader : Option[DecahoseReader] = _

  override def poll(): util.List[SourceRecord] = {
    require(reader.isDefined, "Twitter client not initialized!")
    reader.get.poll()
  }

  override def start(props: util.Map[String, String]): Unit = {
    val sourceConfig = new DecahoseConfig(props)
    reader = Some(new DecahoseReader(config = sourceConfig, context = context))
    println()
  }

  override def stop() = {
    reader.foreach(r=>r.stop())
  }
  override def version(): String = ""
}
