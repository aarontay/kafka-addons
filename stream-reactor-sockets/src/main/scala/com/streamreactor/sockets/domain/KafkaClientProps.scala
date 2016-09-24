package com.streamreactor.sockets.domain

case class KafkaClientProps(topic: String,
                            consumerGroup: String,
                            parititionAndOffset: Seq[PartitionOffset] = Nil,
                            sample: Option[SampleProps] = None)

case class SampleProps(count: Int, rate: Int)