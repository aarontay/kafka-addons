package com.streamreactor.sockets.domain

case class PartitionOffset(partition: Int, offset: Option[Long])
