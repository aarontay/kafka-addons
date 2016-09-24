package com.streamreactor.sockets.domain

import java.util.Calendar
import spray.json.DefaultJsonProtocol

case class HeartBeatMessage(timestamp: String, system: String, message: String)

object HeartBeatMessage {
  def apply(system: String): HeartBeatMessage = HeartBeatMessage(Calendar.getInstance.getTime.toString, system, "heartbeat")
}