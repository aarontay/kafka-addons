package com.streamreactor.sockets.domain

case class StreamMessage(topic: String,
                         partition: Int,
                         timestamp: Long,
                         timestampType: String,
                         key: Option[String],
                         value: Option[String])
